package com.alexa.home.discover.appliances;

import com.alexa.home.discover.AppliancesHeader;

public class Response {
	private AppliancesHeader header;
	private ResponsePayload payload;
	
	public AppliancesHeader getHeader() {
		return this.header;
	}
	public void setHeader(AppliancesHeader header) {
		this.header = header;
	}
	
	public ResponsePayload getPayload() {
		return this.payload;
	}
	public void setPayload(ResponsePayload payload) {
		this.payload = payload;
	}
}
