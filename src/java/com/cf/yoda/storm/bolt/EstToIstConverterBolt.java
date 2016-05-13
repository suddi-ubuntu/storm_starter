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

public class EstToIstConverterBolt extends BaseRichBolt {

	private static final long serialVersionUID = 3092938699134129356L;

	private OutputCollector collector;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map cfg, TopologyContext topologyCtx, OutputCollector outCollector) {
		collector = outCollector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message", "key"));
	}

	@Override
	public void execute(Tuple tuple) {
		System.out.println(getClass().getName() + " execute method started");

		try {
			String sentence = YodaUtils.getStringValue(tuple, 0);
			AccessLog this_log = null;

			this_log = (AccessLog) YodaUtils.fromJsonString(sentence, AccessLog.class);
			String estTimestampString = this_log.getTime_stamp();
			System.out.println("est-time="+estTimestampString);
			String istTimestampString = YodaUtils.convertEstToIst(estTimestampString);
			System.out.println("ist-time="+istTimestampString);
			this_log.setTime_stamp(istTimestampString);
			String key = this_log.getUuid();

			collector.emit(tuple, new Values(this_log.toString(),key));
			collector.ack(tuple);
			System.out.println(getClass().getName() + " execute method completed");

		} catch (Exception e) {
			System.out.println(getClass().getName() + " execute method failed");

			e.printStackTrace();
		}

	}

}
