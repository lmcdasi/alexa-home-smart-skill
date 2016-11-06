package com.alexa.home.discover;

public class AppliancesHeader {
	private String messageId;
	private String name;
	private	String namespace;
	private	String payloadVersion;
	
	public String getMessageId() {
		return this.messageId;
	}
	public void setMessageId(String id) {
		this.messageId = id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName (String name) {
		this.name = name;
	}
	
	public String getNamespace() {
		return this.namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getPayloadVersion() {
		return this.payloadVersion;
	}
	public void setPayloadVersion(String version) {
		this.payloadVersion = version;
	}
}