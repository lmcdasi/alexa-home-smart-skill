package com.alexa.home.cloud;

import java.net.UnknownHostException;

import com.alexa.home.cloud.config.CouldConfig;

public class CloudUrl {
	private String MAIN_URL = CouldConfig.getCouldConfiguration().getServerURL();
	
	public static final String DISCOVER_URL="discover";
	public static final String TURN_ON = "turnon";
	public static final String TURN_OFF = "turnoff";
	
	public CloudUrl() throws UnknownHostException {
	}
	
	public String getDiscoiverUrl() {
		return MAIN_URL + DISCOVER_URL;
	}
	
	public String getTurnOnUrl() {
		return MAIN_URL + TURN_ON;
	}
	
	public String getTurnOffUrl() {
		return MAIN_URL + TURN_OFF;
	}
}
