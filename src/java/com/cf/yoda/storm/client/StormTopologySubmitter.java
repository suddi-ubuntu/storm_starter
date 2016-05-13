package com.cf.yoda.storm.client;

import com.cf.yoda.storm.topology.TimeStampConverterTopologyBuilderFactory;
import com.cf.yoda.storm.topology.TopologyBuilderFactory;
import com.cf.yoda.storm.topology.WordCountTopologyBuilderFactory;
import com.cf.yoda.storm.topology.YodaTopologyBuilderFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;

public class StormTopologySubmitter {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException{
		TopologyBuilderFactory builderFactory = YodaTopologyBuilderFactory.getTopologyBuilder(WordCountTopologyBuilderFactory.class.getName());
		StormTopologySubmitter stormTopologySubmitter = new StormTopologySubmitter();
		stormTopologySubmitter.submitTopology(builderFactory);
	
	}
	

	public void submitTopology(TopologyBuilderFactory topologyBuilderFactory)
			throws AlreadyAliveException, InvalidTopologyException {

		
		String topologyName = topologyBuilderFactory.getTopologyName();
		Config config = topologyBuilderFactory.getConfig();
		StormTopology topology = topologyBuilderFactory.getTopologyBuilder().createTopology();
		if(topologyBuilderFactory.isLocal()){
			LocalCluster localCluster = new LocalCluster();
			localCluster.submitTopology(topologyName, config, topology);
		}else{
			StormSubmitter stormSubmitter = new StormSubmitter();
			stormSubmitter.submitTopology(topologyName, config, topology);	
		}
		
	}

}
