package com.alexa.home.discover.appliances;

import java.util.ArrayList;

public class DiscoveredAppliance {
	ArrayList<String> actions;
	ArrayList<String> additionalApplianceDetails;
	String applianceId;
	String friendlyDescription;
	String friendlyName;
	Boolean isReachable = true;
	String manufacturerName;
	String modelName;
	String version;
	
	public ArrayList<String> getActions() {
		return this.actions;
	}
	public void setActions(ArrayList<String> actions) {
		this.actions=actions;
	}
	
	public ArrayList<String> getAdditionalApplianceDetails() {
		return this.additionalApplianceDetails;
	}
	public void setAdditionalApplianceDetails(ArrayList<String> additionalDetails) {
		this.additionalApplianceDetails = additionalDetails;
	}
	
	public String getApplianceId() {
		return this.applianceId;
	}
	public void setApplianceId(String id) {
		this.applianceId = id;
	}
	
	public String getFriendlyDescription() {
		return this.friendlyDescription;
	}
	public void setFriendlyDescription(String description) {
		this.friendlyDescription = description;
	}
	
	public String getFriendlyName() {
		return this.friendlyName;
	}
	public void setFriendlyName(String name) {
		this.friendlyName = name;
	}
	
	public Boolean getIsReachable() {
		return this.isReachable;
	}
	public void setIsReachable(Boolean reachable) {
		this.isReachable = reachable;
	}
	
	public String getManufacturerName() {
		return this.manufacturerName;
	}
	public void setManufacturerName(String manufacturer) {
		this.manufacturerName = manufacturer;
	}

	public String getModelName() {
		return this.getModelName();
	}
	public void setModelName(String model) {
		this.modelName = model;
	}
	
	public String getVersion() {
		return this.version;
	}
	public void setVersion(String ver) {
		this.version = ver;
	}
}
