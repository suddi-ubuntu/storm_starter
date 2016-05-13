package com.cf.yoda.storm.bolt;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PopulateKafkaBolt<K, V> extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(PopulateKafkaBolt.class);
	private OutputCollector collector;
	public static final String KAFKA_HOST_PORT = "172.20.9.240:9092,172.20.11.175:9092";
	private String TOPIC = "";
	public static final String BOLT_KEY = "key";
	public static final String BOLT_MESSAGE = "message";
	private Producer<K, V> kproducer;
	int count;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map cfg, TopologyContext topologyCtx, OutputCollector outCollector) {
		try {
			System.out.println(java.net.Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(
				"*********************BOLT PREPARE  METHOD STARTED FOR POPULATE KAFKA BOLT*****************************");
		Properties props = new Properties();
		props.put("metadata.broker.list", KAFKA_HOST_PORT);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		TOPIC = (String) cfg.get("topic");
		ProducerConfig config = new ProducerConfig(props);

		// first type: partition key
		// // second type: type of message
		kproducer = new Producer<K, V>(config);
		  count = 0;
		collector = outCollector;
		System.out.println(
				"*********************BOLT PREPARE METHOD COMPLETED FOR POPULATE KAFKA BOLT******************************");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println(
				"*********************BOLT DECLARE METHOD STARTED FOR POPULATE KAFKA BOLT******************************");
		declarer.declare(new Fields("count"));
		System.out.println(
				"*********************BOLT DECLARE METHOD EXIT FOR POPULATE KAFKA BOLT  ******************************");
	}

	@Override
	public void execute(Tuple input) {
		System.out.println(
				"*********************EXECUTE METHOD STARTED FOR POPULATE KAFKA BOLT ****************************");
		K key = null;
		if (input.contains(BOLT_KEY)) {
			key = (K) input.getValueByField(BOLT_KEY);
		}
		System.out.println("***key = " + key);// ***************Execute method
												// started for Populate Kafka
												// Bolt
												// started*****************************");
		System.out.println("********fields=" + input.getFields());
		V message = (V) input.getValueByField(BOLT_MESSAGE);
		// V message = (V) mes(3);

		System.out.println("***message = " + message);
		count = count + 1;
		System.out.println("***Count_For_message_put into kafka = " + count);
		try {
			// count=count+1;
			kproducer.send(new KeyedMessage<K, V>(TOPIC, key, message));
		} catch (Exception ex) {
			LOG.error("Could not send message with key '" + key + "' and value '" + message + "'", ex);
		} finally {
			collector.emit(input, new Values(count));
			collector.ack(input);
		}
		System.out.println(
				"*********************EXECUTE METHOD COMPLETED FOR POPULATE KAFKA BOLT*****************************");

	}

}
