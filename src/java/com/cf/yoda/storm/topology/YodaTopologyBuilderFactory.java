package com.cf.yoda.storm.topology;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

public class YodaTopologyBuilderFactory {
	
	
	public static TopologyBuilderFactory getTopologyBuilder(String topologyName){
		
	    if(topologyName.equals(TimeStampConverterTopologyBuilderFactory.class.getName()))	{
	    	
	    	TimeStampConverterTopologyBuilderFactory timeStampConverterTopologyBuilderFactory = new TimeStampConverterTopologyBuilderFactory("","","");
			return timeStampConverterTopologyBuilderFactory;
	    }else if(topologyName.equals(WordCountTopologyBuilderFactory.class.getName())){
	    	return new WordCountTopologyBuilderFactory();
	    }
	    return null;
	}
	}
