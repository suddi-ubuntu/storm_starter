/**
 * 
 */
package com.cf.yoda.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;

import com.cf.yoda.domain.AccessLog;

import backtype.storm.tuple.Tuple;

/**
 * @author pradyumnakumarjena
 *
 */
public class YodaUtilsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.cf.yoda.util.YodaUtils#fromJsonString(java.lang.String, java.lang.Class)}.
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testFromJsonString() throws JsonParseException, JsonMappingException, IOException {
	String jsonString ="{}";
     AccessLog accessLog = (AccessLog) YodaUtils.fromJsonString(jsonString, AccessLog.class);
     assertNotNull(accessLog);
     
     jsonString = "{\"@version\":\"1.2.1\"}";
     accessLog = (AccessLog) YodaUtils.fromJsonString(jsonString, AccessLog.class);
     assertNotNull(accessLog);
     assertEquals("1.2.1", accessLog.getVersion());
     
	}

	/**
	 * Test method for {@link com.cf.yoda.util.YodaUtils#toJsonString(com.cf.yoda.domain.AccessLog)}.
	 */
	@Test
	public void testToJsonString() {
      AccessLog accessLog = new AccessLog();
      accessLog.setVersion("1.2.1");
      String jsonString = YodaUtils.toJsonString(accessLog);
      assertTrue( jsonString.contains("\"@version\":\"1.2.1\""));
	}

	/**
	 * Test method for {@link com.cf.yoda.util.YodaUtils#convertEstToIst(java.lang.String)}.
	 * @throws ParseException 
	 */
	@Test
	public void testConvertEstToIst() throws ParseException {
		String[] months = {"Jan","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		for(String month :months){
		String estTimestampString = "30/"+month+"/2015:07:38:21 -0500";
		 
		String istTimestampString = YodaUtils.convertEstToIst(estTimestampString);
		 assertEquals("30/"+month+"/2015:18:08:21 +0530", istTimestampString);
		}
	}
	
	
	
	/**
	 * Test method for {@link com.cf.yoda.util.YodaUtils#convertEstToIst(java.lang.String)}.
	 * @throws ParseException 
	 */
	@Test
	public void testConvertIstToEst() throws ParseException {
		String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		for(String month :months){
		String istTimestampString = "12/"+month+"/2015:11:04:54 +0530";
		 
		String estTimestampString = YodaUtils.convertIstToEst(istTimestampString);
		assertEquals("12/"+month+"/2015:00:34:54 -0500", estTimestampString);
		}
	}


	/**
	 * Test method for {@link com.cf.yoda.util.YodaUtils#getStringValue(backtype.storm.tuple.Tuple, int)}.
	 */
	@Test
	public void testGetStringValue() {
		Tuple tuple = mock(Tuple.class);
		// set expectation
		expect(tuple.getValue(0)).andReturn("hello".getBytes());
		//replay mocks
		replay(tuple);
		String returnString = YodaUtils.getStringValue(tuple , 0);
		//verify mocks
		verify(tuple);
		assertEquals("hello", returnString);
		
		tuple = mock(Tuple.class);
		// set expectation
		expect(tuple.getValue(0)).andReturn("hello");
		//replay mocks
		replay(tuple);
		  returnString = YodaUtils.getStringValue(tuple , 0);
		//verify mocks
		verify(tuple);
		assertEquals("hello", returnString);
		
	}

}
