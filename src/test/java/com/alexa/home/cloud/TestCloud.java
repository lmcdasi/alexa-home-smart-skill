package com.alexa.home.cloud;

import org.junit.Test;

public class TestCloud {
	@Test
	public void postTurnOnRequest() throws Exception {
		SSLClientApplication client = new SSLClientApplication();
	
		client.sendPost(CloudUrl.TURN_ON, "ACCESS TOKEN - RUNTIME");
	}
	
	@Test
	public void postTurnOffRequest() throws Exception {
		SSLClientApplication client = new SSLClientApplication();
		
		client.sendPost(CloudUrl.TURN_OFF, "ACCESS TOKEN - RUNTIME");
	}
}
