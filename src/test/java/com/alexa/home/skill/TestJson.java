package com.alexa.home.skill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.junit.Test;

import com.alexa.home.discover.appliances.Request;
import com.alexa.home.discover.appliances.Response;
import com.alexa.home.skill.AWSAdaptor;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class TestJson {
	private static Logger logger = Logger.getLogger(TestJson.class);
	
	@Before
	public void starting() {
		PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");
		ConsoleAppender appender = new ConsoleAppender(layout);
		//appender.setWriter(new PrintWriter(System.out));
		
		Logger.getRootLogger().addAppender(appender);
	}
	
	public File discoverAppliancesRequest() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("DiscoverAppliancesRequest.json").getFile());
	}
	
	@Test
	public void testDiscoveryRequest() throws FileNotFoundException, Exception {
		JsonReader reader = new JsonReader(new FileReader(discoverAppliancesRequest()));
		Gson gson = new Gson();
		Request req = gson.fromJson(reader, Request.class);
		
		AWSAdaptor adaptor = new AWSAdaptor();
		Response rsp = adaptor.handleDiscovery(req);
		
		 String strRsp = gson.toJson(rsp, Response.class);
		 logger.debug("Json rsp: " + strRsp);
	}
	
	public File turnOnRequest() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("TurnOnRequest.json").getFile());
	}
	
	@Test
	public void testTurnOn() throws FileNotFoundException, Exception {
		JsonReader reader = new JsonReader(new FileReader(turnOnRequest()));
		Gson gson = new Gson();
		Request req = gson.fromJson(reader, Request.class);
		
		AWSAdaptor adaptor = new AWSAdaptor();
		Response rsp = adaptor.handleOnRequest(req);
		
		 String strRsp = gson.toJson(rsp, Response.class);
		 logger.debug("Json rsp: " + strRsp);
	}
	
	public File turnOffRequest() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("TurnOffRequest.json").getFile());
	}
	
	@Test
	public void testTurnOff() throws FileNotFoundException, Exception {
		JsonReader reader = new JsonReader(new FileReader(turnOffRequest()));
		Gson gson = new Gson();
		Request req = gson.fromJson(reader, Request.class);
		
		AWSAdaptor adaptor = new AWSAdaptor();
		Response rsp = adaptor.handleOffRequest(req);
		
		 String strRsp = gson.toJson(rsp, Response.class);
		 logger.debug("Json rsp: " + strRsp);
	}
}
