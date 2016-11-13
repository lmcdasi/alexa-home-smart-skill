package com.alexa.home.cloud;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.restlet.ext.gson.GsonRepresentation;
import org.restlet.representation.Representation;

import com.alexa.home.discover.AppliancesHeader;
import com.alexa.home.discover.appliances.DiscoveredAppliance;
import com.alexa.home.discover.appliances.Response;
import com.alexa.home.discover.appliances.ResponsePayload;
import com.google.gson.Gson;

public class TestCloud {
    private static final Logger logger = Logger.getLogger(TestCloud.class.getCanonicalName());
    
    private static final String RESPONSE_DISCOVER = "DiscoverAppliancesResponse";
    
    @Test
    public void postDiscoverRequest() throws Exception {       
        Response rsp = new Response();
        AppliancesHeader head = new AppliancesHeader();
        head.setMessageId("111");
        head.setName("test");
        head.setNamespace("test namespace");
        head.setPayloadVersion("1.1");
        rsp.setHeader(head);
        rsp.getHeader().setName(RESPONSE_DISCOVER);
        ResponsePayload rspPayload = new ResponsePayload();
        rsp.setPayload(rspPayload);
        
        try (SSLClientApplication client = new SSLClientApplication()) {
            Representation discoveredAppliances = client.sendPost(CloudUrl.DISCOVER_URL, "ACCESS TOKEN - RUNTIME");
            GsonRepresentation<DiscoveredAppliance[]> representation = new GsonRepresentation<DiscoveredAppliance[]>(discoveredAppliances, DiscoveredAppliance[].class);
                        
            DiscoveredAppliance[] appliances = representation.getObject();
            for (DiscoveredAppliance appliance : appliances) {
                rspPayload.setDiscoveredAppliances(appliance);
            }
            
            Gson gson = new Gson();
            String strRsp = gson.toJson(rsp, Response.class);
            logger.debug("Json rsp: " + strRsp);
        }
    }
    
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
