package com.cf.yoda.storm.topology;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.zookeeper.KeeperException;
import org.elasticsearch.storm.EsBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.stormspout.InitialPositionInStream;
import com.amazonaws.services.kinesis.stormspout.KinesisSpout;
import com.amazonaws.services.kinesis.stormspout.KinesisSpoutConfig;
import com.cf.yoda.storm.bolt.decodeBolt;
import com.cf.yoda.storm.spout.YodaKinesisSpout;


public class KinesisTestTopology {
	 private static final Logger LOG = LoggerFactory.getLogger(KinesisTestTopology.class);
	    private static String topologyName = "KinesisTopology";
	    private static String streamName;
	    
	    private static String esNodes;
	    //private static String esInputJson;
	    private static int spoutNumShards;
	    private static String esIndexByType;
	    private static int numExecutorDecodeBolt;
	    
	    private static InitialPositionInStream initialPositionInStream = InitialPositionInStream.LATEST;
	    private static int recordRetryLimit = 3;
	    private static Regions region = Regions.AP_SOUTHEAST_1;
	    private static String zookeeperEndpoint;
	    private static String zookeeperPrefix;
	    private static String awsCredentialFilePath="/home/commonfloor/AwsCredentials.properties";//KinesisTestTopology.class.getResource("AwsCredentials.properties").getPath();

	    public static void main(String[] args) throws IllegalArgumentException, KeeperException, InterruptedException, AlreadyAliveException, InvalidTopologyException, IOException {
	        String propertiesFile = null;
	        
	        
	        //String mode = null;
	        
	        //if (args.length != 2) {
	         //   printUsageAndExit();
	        //} else {
	            propertiesFile = args[0];
	          //  mode = args[1];
	        //}
	        
	        configure(propertiesFile);


	         final KinesisSpoutConfig kinesisConfig =
	                new KinesisSpoutConfig(streamName,zookeeperEndpoint).withZookeeperPrefix(zookeeperPrefix)
	                        .withKinesisRecordScheme(new SampleKinesisRecordScheme())
	                        .withInitialPositionInStream(initialPositionInStream)
	                        .withRecordRetryLimit(recordRetryLimit)
	                        .withRegion(region);

	        final YodaKinesisSpout spout = new YodaKinesisSpout(kinesisConfig, new CustomCredentialsProviderChain(), new ClientConfiguration());
	        TopologyBuilder builder = new TopologyBuilder();
	        LOG.info("Using Kinesis stream: " + kinesisConfig.getStreamName());
	        Map configElastic = new HashMap();
	        configElastic.put("es.nodes", esNodes);
	        configElastic.put("es.input.json", "true");
	        //configElastic.put("es.resource.write"," my-collection/{@timestamp:YYYY.MM.dd}");
	        // Using number of shards as the parallelism hint for the spout.
	        builder.setSpout("kinesis_spout", spout,spoutNumShards );
	        builder.setBolt("decode_bolt", new decodeBolt(), numExecutorDecodeBolt).shuffleGrouping("kinesis_spout");
            builder.setBolt("es-Bolt", new EsBolt(esIndexByType,configElastic),5).shuffleGrouping("decode_bolt");
	        Config conf = new Config();
	        //conf.put(Config.STORM_ZOOKEEPER_PORT,2181);
	        //conf.setFallBackOnJavaSerialization(true);
	        //conf.setDebug(true);
	        
	        System.out.println("my conf:"+conf);
	        if (args != null && args.length > 1) {
	  	      conf.setNumWorkers(3);
	  	      topologyName = (String)args[1];
	
	  	      StormSubmitter.submitTopologyWithProgressBar(topologyName, conf, builder.createTopology());
	  	    }
	  	    else{

	  	       System.out.println(".........................new object for LocalCluster.................................");
	  	      LocalCluster cluster = new LocalCluster();
	  	                 
	  	      System.out.println(".........##################SUBMITING TOPOLOGY with the above conf, ################.................................");
	  	     
	  	      cluster.submitTopology(topologyName, conf, builder.createTopology());
	  	      System.out.println("######################");
	  	      System.out.println("#################SUCCESSFULLY SUBMITTED TOPOLOGY>>>>>>>>>>>TIME TO SLEEP   [I AM WAITING]  <<<<<<<<<<<<<<#####");
	  	      Utils.sleep(3000000);
	  	      System.out.println("####################OK !!! HOPE YOU ARE DONE###########");
	  	      //cluster.killTopology(TOPOLOGY_NAME);
	  	     
	  	      cluster.shutdown();
	  	      cluster.killTopology(topologyName);
	  	    }
	    }

	        
	        private static void configure(String propertiesFile) throws IOException {
	            FileInputStream inputStream = new FileInputStream(propertiesFile);
	            Properties properties = new Properties();
	            try {
	                properties.load(inputStream);
	            } finally {
	                inputStream.close();
	            }

	            String topologyNameOverride = properties.getProperty(ConfigKeys.TOPOLOGY_NAME_KEY);
	            if (topologyNameOverride != null) {
	                topologyName = topologyNameOverride;
	            }
	            LOG.info("Using topology name " + topologyName);

	            String streamNameOverride = properties.getProperty(ConfigKeys.STREAM_NAME_KEY);
	            if (streamNameOverride != null) {
	                streamName = streamNameOverride;
	            }
	            LOG.info("Using stream name " + streamName);

	            String initialPositionOverride = properties.getProperty(ConfigKeys.INITIAL_POSITION_IN_STREAM_KEY);
	            if (initialPositionOverride != null) {
	                 initialPositionInStream = InitialPositionInStream.valueOf(initialPositionOverride);
	            }
	            LOG.info("Using initial position " + initialPositionInStream.toString() + " (if a checkpoint is not found).");
	            
	            String recordRetryLimitOverride = properties.getProperty(ConfigKeys.RECORD_RETRY_LIMIT);
	            if (recordRetryLimitOverride != null) {
	                recordRetryLimit = Integer.parseInt(recordRetryLimitOverride.trim());
	            }
	            LOG.info("Using recordRetryLimit " + recordRetryLimit);

	            String regionOverride = properties.getProperty(ConfigKeys.REGION_KEY);
	            if (regionOverride != null) {
	                region = Regions.fromName(regionOverride);
	            }
	            LOG.info("Using region " + region.getName());

	            String zookeeperEndpointOverride = properties.getProperty(ConfigKeys.ZOOKEEPER_ENDPOINT_KEY);
	            
	            if (zookeeperEndpointOverride != null) {
	                zookeeperEndpoint = zookeeperEndpointOverride;
	            }
	            LOG.info("Using zookeeper endpoint " + zookeeperEndpoint);

	            String zookeeperPrefixOverride = properties.getProperty(ConfigKeys.ZOOKEEPER_PREFIX_KEY);
	            if (zookeeperPrefixOverride != null) {            
	                zookeeperPrefix = zookeeperPrefixOverride;
	            }
	            LOG.info("Using zookeeper prefix " + zookeeperPrefix);
	            
	            String esNodesOverride = properties.getProperty("esNodes");
	            if (esNodesOverride != null) {            
	            	esNodes = esNodesOverride;
	            }
	            LOG.info("Using esNodes  " + esNodes);
	            
	            String esIndexByTypeOverride = properties.getProperty("esIndexByType");
	            if (esIndexByTypeOverride != null) {            
	            	esIndexByType = esIndexByTypeOverride;
	            }
	            LOG.info("Using esIndexByType  " + esIndexByType);
	            
	            String spoutNumShardsOverride = properties.getProperty("spoutNumShards");
	            if (spoutNumShardsOverride != null) {
	            	spoutNumShards = Integer.parseInt(spoutNumShardsOverride.trim());
	            }
	            LOG.info("Using spoutNumShards " + spoutNumShards);
	            
	            
	            String numExecutorDecodeBoltOverride = properties.getProperty("numExecutorDecodeBolt");
	            if (numExecutorDecodeBoltOverride != null) {
	            	numExecutorDecodeBolt = Integer.parseInt(numExecutorDecodeBoltOverride.trim());
	            }
	            LOG.info("Using numExecutorDecodeBolt " + numExecutorDecodeBolt);
	            
	      
	        
	        
	    }
}
	   // private static void printUsageAndExit() {
	     //   System.out.println("Usage: " + KinesisTopology.class.getName() + " <propertiesFile> <LocalMode or RemoteMode>");
	       // System.exit(-1);
	    //}



