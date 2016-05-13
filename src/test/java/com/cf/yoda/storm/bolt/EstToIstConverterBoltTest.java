package com.cf.yoda.storm.bolt;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cf.yoda.domain.AccessLog;
import com.cf.yoda.util.YodaUtils;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class EstToIstConverterBoltTest extends EasyMockSupport{
	
	private EstToIstConverterBolt target ;
	
	 private OutputCollector outCollector;
	private TopologyContext topologyCtx;
	private Tuple tuple ;
	
	private Map cfg;

	@Before
	public void setUp() throws Exception {
		cfg = new HashMap<>();
		tuple = niceMock(Tuple.class);
		topologyCtx = niceMock(TopologyContext.class);
		outCollector = niceMock(OutputCollector.class);
		
		target = new EstToIstConverterBolt();
		
		target.prepare(cfg, topologyCtx, outCollector);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() throws JsonParseException, JsonMappingException, IOException {
		AccessLog accessLog = new AccessLog();
		String estTimestamp = "30/Sep/2015:16:04:54 -0500";
		String istTimestamp = "01/Oct/2015:02:34:54 +0530";
		
		accessLog.setTime_stamp(estTimestamp);
		Capture<Tuple> capturedTuple = newCapture();
		Capture<Values> capturedValues = newCapture();
		expect(tuple.getValue(0)).andReturn(YodaUtils.toJsonString(accessLog));
		expect(outCollector.emit(EasyMock.<Tuple>capture(capturedTuple), EasyMock.<Values>capture(capturedValues))).andReturn(new ArrayList<Integer>());
		
		//set expectations 
		replayAll();
		
		//execute
		target.execute(tuple );
		
		//verify
		verifyAll();
		
		Values values = capturedValues.getValue();
		String jsonString = (String) values.get(0);
		AccessLog newAccessLog = (AccessLog) YodaUtils.fromJsonString(jsonString, AccessLog.class);
		
		assertEquals(istTimestamp, newAccessLog.getTime_stamp());
		
	}

}
