package com.cf.yoda.storm.topology;
import com.cf.yoda.storm.bolt.ReportBolt;
import com.cf.yoda.storm.bolt.RequestCountBolt;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import java.util.HashMap;
import java.util.Map;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

public class RequestCountPerServerTopology {
	
	  private static String TOPOLOGY_NAME = "RequestCountPerServer topology";

	  public static void main(String[] args) throws Exception {
	    int numSpoutExecutors = 1;
	    String KafkaInputTopic = args[0];
	    System.out.println("input topic="+KafkaInputTopic);
	    System.out.println(".........................building object for kafka spout.....[for taking data from kafka topic]................");
	    KafkaSpout kspout = buildKafkaSentenceSpout(KafkaInputTopic);
	    RequestCountBolt requestCountBolt = new RequestCountBolt();
	    //WordCountBolt countBolt = new WordCountBolt();

	    ReportBolt reportBolt = new ReportBolt();
	    
	    TopologyBuilder builder = new TopologyBuilder();
	    
	    builder.setSpout("kafka-spout", kspout, numSpoutExecutors);
	 
	    builder.setBolt("count-request-bolt", requestCountBolt).shuffleGrouping("kafka-spout");
	    builder.setBolt("report-bolt", reportBolt).shuffleGrouping("count-request-bolt");

	   //System.out.println(".........................setting report Bolt................................");
	    //builder.setBolt("acking-report-bolt", reportBolt).globalGrouping();
	    
	    System.out.println(".........................defining new Config Object.................................");
	    Config conf = new Config();    
	    
	    //Map conf = new HashMap();
	    if (args != null && args.length > 2) {
	      conf.setNumWorkers(3);
	      TOPOLOGY_NAME = (String)args[2];
	      StormSubmitter.submitTopologyWithProgressBar(TOPOLOGY_NAME, conf, builder.createTopology());
	    }
	    else{

	       System.out.println(".........................new object for LocalCluster.................................");
	      LocalCluster cluster = new LocalCluster();
	                 
	      System.out.println(".........##################SUBMITING TOPOLOGY with the above conf, ################.................................");
	     
	      cluster.submitTopology(TOPOLOGY_NAME, conf, builder.createTopology());
	      System.out.println("######################");
	      System.out.println("#################SUCCESSFULLY SUBMITTED TOPOLOGY>>>>>>>>>>>TIME TO SLEEP   [I AM WAITING]  <<<<<<<<<<<<<<#####");
	      Utils.sleep(30000);
	      System.out.println("####################OK !!! HOPE YOU ARE DONE###########");
	      //cluster.killTopology(TOPOLOGY_NAME);
	     
	      cluster.shutdown();
	      cluster.killTopology(TOPOLOGY_NAME);
	    }
	 

	   //StormSubmitter.submitTopologyWithProgressBar(TOPOLOGY_NAME, cfg, builder.createTopology());
	  }

	  private static KafkaSpout buildKafkaSentenceSpout(String Topic) {
	    String zkHostPort = "XXXXX:XXX:XX:XX:2181";
	    String topic = Topic;

	    String zkRoot = "/tmp/kafka-spout";
	    String zkSpoutId = "accessSpout";
	    ZkHosts zkHosts = new ZkHosts(zkHostPort);
	    
	    SpoutConfig spoutCfg = new SpoutConfig(zkHosts, topic, zkRoot, zkSpoutId);
	    KafkaSpout kafkaSpout = new KafkaSpout(spoutCfg);
	    return kafkaSpout;
	  }

}
