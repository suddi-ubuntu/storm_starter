package com.cf.yoda.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cf.yoda.domain.AccessLog;

import backtype.storm.tuple.Tuple;

public class YodaUtils {

	private static final String ACCESSLOG_TIMESTAMP_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
	public static final SimpleDateFormat EST_Formatter = getESTFormmater();
	public static final SimpleDateFormat IST_Formatter = getISTFormatter();

	private static SimpleDateFormat getESTFormmater() {
		SimpleDateFormat estFormatter = new SimpleDateFormat(ACCESSLOG_TIMESTAMP_FORMAT);
		TimeZone estTimezone = TimeZone.getTimeZone("EST");
		estFormatter.setTimeZone(estTimezone);
		return estFormatter;

	}

	private static SimpleDateFormat getISTFormatter() {
		SimpleDateFormat istFormatter = new SimpleDateFormat(ACCESSLOG_TIMESTAMP_FORMAT);
		TimeZone istTimezone = TimeZone.getTimeZone("IST");
		istFormatter.setTimeZone(istTimezone);
		return istFormatter;
	}

	public static void main(String[] args) throws ParseException {
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		for (String month : months) {
			String estTimestampString = "12/" + month + "/2015:01:04:54 -0500";
			String istString = convertEstToIst(estTimestampString);
			System.out.println(istString);
		}
	}

	public static Object fromJsonString(String string, Class clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Object instance = mapper.readValue(string.getBytes(), clazz);
		return instance;

	}

	public static String toJsonString(AccessLog accessLog) {

		String jsonString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
					.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
					.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
			jsonString = mapper.writeValueAsString(accessLog);
		} catch (Exception e) {
			System.out.println("failed to serilaize ");
		}
		return jsonString;
	}

	public static String convertEstToIst(String estTimestampString) throws ParseException {
        System.out.println("input timestamp:"+estTimestampString);
		Date estDate = EST_Formatter.parse(estTimestampString);

		String istTimestampString = IST_Formatter.format(estDate);
		System.out.println("output timestamp:"+istTimestampString);
		return istTimestampString;

	}
	
	
	
	public static String convertIstToEst(String istTimestampString) throws ParseException {
        System.out.println("input timestamp:"+istTimestampString);
		Date istDate = IST_Formatter.parse(istTimestampString);

		String estTimestampString = EST_Formatter.format(istDate);
		System.out.println("output timestamp:"+estTimestampString);
		return estTimestampString;

	}


	public static String getStringValue(Tuple tuple, int index) {
		Object value = tuple.getValue(index);
		String sentence = null;
		if (value instanceof String) {
			sentence = (String) value;

		} else {
			// Kafka returns bytes
			byte[] bytes = (byte[]) value;
			try {
				sentence = new String(bytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return sentence;
	}

}
