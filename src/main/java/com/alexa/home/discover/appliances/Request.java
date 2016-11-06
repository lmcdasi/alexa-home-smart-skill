package com.alexa.home.discover.appliances;

import com.alexa.home.discover.AppliancesHeader;

public class Request {
	private AppliancesHeader header;
	private RequestPayload payload;
	
	public AppliancesHeader getHeader() {
		return this.header;
	}
	
	public RequestPayload getPayload() {
		return this.payload;
	}
}
