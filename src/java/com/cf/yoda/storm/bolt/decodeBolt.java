package com.cf.yoda.storm.bolt;



import java.util.Map;

import com.cf.yoda.domain.AccessLog;
import com.cf.yoda.storm.topology.SampleKinesisRecordScheme;
import com.cf.yoda.util.YodaUtils;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.task.OutputCollector;

import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.amazonaws.services.kinesis.model.Record;

public class decodeBolt extends BaseBasicBolt {
	
	 private static final long serialVersionUID = 177788290277634153L;
	 private static final Logger LOG = LoggerFactory.getLogger(decodeBolt.class);
     private transient CharsetDecoder decoder;
   


	@Override
	public void prepare(Map cfg, TopologyContext context) {
		System.out.println("##$$$$$$$$$$$$$$$$$$$$prepare method called...$$$$$$$$");
		decoder = Charset.forName("UTF-8").newDecoder();
		

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("##$$$$$$$$$$$$$$$$$$$$ Declare----method called...$$$$$$$$");
		declarer.declare(new Fields("recordData"));
	}

	@Override
	public void execute(Tuple input,BasicOutputCollector collector) {
		System.out.println(getClass().getName() + " ####################execute method started###############");

		String partitionKey = (String)input.getValueByField(SampleKinesisRecordScheme.FIELD_PARTITION_KEY);
        String sequenceNumber = (String)input.getValueByField(SampleKinesisRecordScheme.FIELD_SEQUENCE_NUMBER);
        byte[] payload = (byte[])input.getValueByField(SampleKinesisRecordScheme.FIELD_RECORD_DATA);
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        String data = null; 
        try {
            data = decoder.decode(buffer).toString();
        } catch (CharacterCodingException e) {
            LOG.error("Exception when decoding record ", e);
        }
        LOG.info("SampleBolt got record: partitionKey=" + partitionKey + ", " + " sequenceNumber=" + sequenceNumber
                + ", data=" + data);
        collector.emit(new Values(data));

	}


}