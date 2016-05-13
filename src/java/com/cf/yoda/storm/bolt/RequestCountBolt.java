package com.cf.yoda.storm.bolt;

import java.util.Map;
import java.util.HashMap;

import com.cf.yoda.domain.AccessLog;
import com.cf.yoda.util.YodaUtils;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class RequestCountBolt extends BaseRichBolt {

	private static final long serialVersionUID = 3092938699134129356L;
	private static final Long ONE = Long.valueOf(1);
	private Map<String, Long> countMap;
	private OutputCollector collector;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map cfg, TopologyContext topologyCtx, OutputCollector outCollector) {
		collector = outCollector;
		countMap = new HashMap<>();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("Server_Name","count"));
	}

	@Override
	public void execute(Tuple tuple) {
		System.out.println(getClass().getName() + " execute method started");

		try {
			String sentence = YodaUtils.getStringValue(tuple, 0);
			AccessLog this_log = null;

			this_log = (AccessLog) YodaUtils.fromJsonString(sentence, AccessLog.class);

			String Server_Name = this_log.getHost();
		    String time = this_log.getTime_stamp();
		    Server_Name =Server_Name + "_" + time.substring(12,14);

		    if( Integer.parseInt(time.substring(15,17)) < 30 )
		        Server_Name = Server_Name +":00";
		        else Server_Name = Server_Name +":30";


		    Long cnt = countMap.get(Server_Name);
		    if (cnt == null) {
		      cnt = ONE;
		    } else {
		      cnt++;
		    }
		    countMap.put(Server_Name, cnt);
		    System.out.println("server name = " + Server_Name + " ....count..= "+countMap.get(Server_Name));
		    collector.emit(tuple, new Values(Server_Name, cnt));
		    collector.ack(tuple);

			
			
			
			System.out.println(getClass().getName() + " execute method completed");

		} catch (Exception e) {
			System.out.println(getClass().getName() + " execute method failed");

			e.printStackTrace();
		}

	}

}












