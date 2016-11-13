package com.alexa.home.discover.appliances;

import java.util.ArrayList;

public class ResponsePayload {
	private ArrayList<DiscoveredAppliance> discoveredAppliances = new ArrayList<DiscoveredAppliance>();
	
	public ArrayList<DiscoveredAppliance> getDiscoveredAppliances() {
		return this.discoveredAppliances;
	}
	public void setDiscoveredAppliances(DiscoveredAppliance appliance) {
	    this.discoveredAppliances.add(appliance);
	}
	
	public void setDiscoveredAppliances() {
		DiscoveredAppliance appliance = new DiscoveredAppliance();	
		ArrayList<String> actions = new ArrayList<String>();
		actions.add("turnOn");
		actions.add("turnOff");
		
		appliance.setActions(actions);
		appliance.setApplianceId("101");
		appliance.setFriendlyDescription("home garage");
		appliance.setFriendlyName("garage");
		appliance.setIsReachable(true);
		appliance.setManufacturerName("diy");
		appliance.setModelName("rpi");
		appliance.setVersion("0.1");
		
		this.discoveredAppliances.add(appliance);
	}
}
