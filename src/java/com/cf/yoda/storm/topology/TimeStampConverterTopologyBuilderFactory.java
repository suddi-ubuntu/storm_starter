package com.cf.yoda.storm.topology;

import com.cf.yoda.storm.bolt.EstToIstConverterBolt;
import com.cf.yoda.storm.bolt.PopulateKafkaBolt;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class TimeStampConverterTopologyBuilderFactory implements TopologyBuilderFactory {
	private   String topologyName = "TimeConverterIST topology";
	private String KafkaInputTopic =null;
	private String KafkaOutputTopic = null;
	private Number numSpoutExecutors;
	

	public TimeStampConverterTopologyBuilderFactory(String topologyName, String kafkaInputTopic,
			String kafkaOutputTopic) {
		super();
		this.topologyName = topologyName;
		KafkaInputTopic = kafkaInputTopic;
		KafkaOutputTopic = kafkaOutputTopic;
	}
	
	

	public String getKafkaInputTopic() {
		return KafkaInputTopic;
	}



	public void setKafkaInputTopic(String kafkaInputTopic) {
		KafkaInputTopic = kafkaInputTopic;
	}



	public String getKafkaOutputTopic() {
		return KafkaOutputTopic;
	}



	public void setKafkaOutputTopic(String kafkaOutputTopic) {
		KafkaOutputTopic = kafkaOutputTopic;
	}



	public Number getNumSpoutExecutors() {
		return numSpoutExecutors;
	}



	public void setNumSpoutExecutors(Number numSpoutExecutors) {
		this.numSpoutExecutors = numSpoutExecutors;
	}



	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}


 
	private   KafkaSpout buildKafkaSentenceSpout(String Topic) {
		String zkHostPort = "XXXXXX:2181";
		String topic = Topic;

		String zkRoot = "/tmp/kafka-spout";
		String zkSpoutId = "accessSpout";
		ZkHosts zkHosts = new ZkHosts(zkHostPort);

		SpoutConfig spoutCfg = new SpoutConfig(zkHosts, topic, zkRoot, zkSpoutId);
		KafkaSpout kafkaSpout = new KafkaSpout(spoutCfg);
		return kafkaSpout;
	}

	@Override
	public TopologyBuilder getTopologyBuilder() {
		KafkaSpout kspout = buildKafkaSentenceSpout(KafkaInputTopic);
		EstToIstConverterBolt estToIstBolt = new EstToIstConverterBolt();
		PopulateKafkaBolt kafkaBolt = new PopulateKafkaBolt();

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("kafka-spout", kspout, numSpoutExecutors);

		builder.setBolt("convert-to-ist-bolt", estToIstBolt).shuffleGrouping("kafka-spout");
		builder.setBolt("write-to-kafkak-bolt", kafkaBolt).shuffleGrouping("convert-to-ist-bolt");
		return builder;

	}

	@Override
	public Config getConfig() {
		Config conf = new Config();
		conf.put("topic", KafkaOutputTopic);
		conf.setNumWorkers(3);
		return conf;
		
	}

	@Override
	public String getTopologyName() {
		return topologyName;
	}



	@Override
	public boolean isLocal() {
		// TODO Auto-generated method stub
		return false;
	}
}