package com.cf.yoda.storm.topology;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

public interface TopologyBuilderFactory {
	
	public   TopologyBuilder getTopologyBuilder();
	
	public Config getConfig();
	
	public String getTopologyName();
	
	public boolean isLocal();

}
