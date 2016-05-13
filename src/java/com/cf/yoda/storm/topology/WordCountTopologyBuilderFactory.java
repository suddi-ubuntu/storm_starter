package com.cf.yoda.storm.topology;

import com.cf.yoda.storm.bolt.SplitSentenceBolt;
import com.cf.yoda.storm.bolt.WordCountBolt;
import com.cf.yoda.storm.spout.RandomSentenceSpout;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopologyBuilderFactory implements TopologyBuilderFactory {

	@Override
	public TopologyBuilder getTopologyBuilder() {
	    TopologyBuilder builder = new TopologyBuilder();
	    //and parallelism hint of 5 executors
	    builder.setSpout("spout", new RandomSentenceSpout(), 5);
	    //Add the SplitSentence bolt, with a name of 'split'
	    //and parallelism hint of 8 executors
	    //shufflegrouping subscribes to the spout, and equally distributes
	    //tuples (sentences) across instances of the SplitSentence bolt
	    builder.setBolt("split", new SplitSentenceBolt(), 8).shuffleGrouping("spout");
	    //Add the counter, with a name of 'count'
	    //and parallelism hint of 12 executors
	    //fieldsgrouping subscribes to the split bolt, and
	    //ensures that the same word is sent to the same instance (group by field 'word')
	    builder.setBolt("count", new WordCountBolt(), 12).fieldsGrouping("split", new Fields("word"));
	    
	    return builder;
	
	}

	@Override
	public Config getConfig() {
		//new configuration
	    Config conf = new Config();
	    conf.setDebug(true);
	    
	  //Cap the maximum number of executors that can be spawned
	      //for a component to 3
	      conf.setMaxTaskParallelism(3);
	      

	    return conf;

	}

	@Override
	public String getTopologyName() {
		return "word-count";
	}

	@Override
	public boolean isLocal() {
		return true;
	}

}
