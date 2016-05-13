package com.cf.yoda.storm.spout;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.kinesis.stormspout.KinesisSpout;
import com.amazonaws.services.kinesis.stormspout.KinesisSpoutConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;


import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.stormspout.state.IKinesisSpoutStateManager;
import com.amazonaws.services.kinesis.stormspout.state.zookeeper.ZookeeperStateManager;
import com.google.common.collect.ImmutableList;

public class YodaKinesisSpout extends KinesisSpout{

	public YodaKinesisSpout(KinesisSpoutConfig config, AWSCredentialsProvider credentialsProvider,
			ClientConfiguration clientConfiguration) {
		super(config, credentialsProvider, clientConfiguration);
		// TODO Auto-generated constructor stub
	}
@Override
public void nextTuple() {
 System.out.println("#******************!@@!@!@!@!@!@!@!@!@!@******************next Tuple Invoked**************");
	
	super.nextTuple();
}

public void activate() {
	 System.out.println("#**************@!@!@!@!@!@!@!@!@!@!@!**********************Activate method Invoked**************");
		
		super.activate();
		System.out.println("#**************@!!@!@!@!@!@!@!@!@!@!@@activation Complete!!!!!!!@**********#");
	}

public void deactivate(){
System.out.println("#********************!@!@!@!@!@!@!@!@!@@@*****deactivate method Invoked*********************#");
   
super.deactivate();

}



}
