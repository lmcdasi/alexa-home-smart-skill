package com.alexa.home.skill;

import com.alexa.home.cloud.CloudUrl;
import com.alexa.home.cloud.SSLClientApplication;
import com.alexa.home.discover.appliances.Request;
import com.alexa.home.discover.appliances.Response;
import com.alexa.home.discover.appliances.ResponsePayload;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class AWSAdaptor implements RequestStreamHandler {
	private static Logger logger = Logger.getLogger(AWSAdaptor.class);
  
	// namespaces
    private static final String NAMESPACE_CONTROL = "Alexa.ConnectedHome.Control";
    private static final String NAMESPACE_DISCOVERY = "Alexa.ConnectedHome.Discovery";
    
    // discovery
    private static final String REQUEST_DISCOVER = "DiscoverAppliancesRequest";
    private static final String RESPONSE_DISCOVER = "DiscoverAppliancesResponse";
    
    // control
    private static final String REQUEST_TURN_ON = "TurnOnRequest";
    private static final String RESPONSE_TURN_ON = "TurnOnConfirmation";
    private static final String REQUEST_TURN_OFF = "TurnOffRequest";
    private static final String RESPONSE_TURN_OFF = "TurnOffConfirmation";
    
    //private static final String ERROR_UNSUPPORTED_OPERATION = "UnsupportedOperationError";
    private static final String ERROR_UNEXPECTED_INFO = "UnexpectedInformationReceivedError";
    private static final String ERROR_UNEXPECTED_EXCEPTION = "UnexpectedExceptionError";
	
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		Gson gson = new Gson();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		Request req = gson.fromJson(reader, Request.class);
        logger.debug("Json req: " + gson.toJson(req, Request.class));
        
		Response rsp = null;
		
		try {	
			switch (req.getHeader().getNamespace()) {
				case NAMESPACE_DISCOVERY: {
					rsp = handleDiscovery(req);
					break;
				}
				case NAMESPACE_CONTROL: {
					switch(req.getHeader().getName()) {
						case REQUEST_TURN_ON: {
							rsp = handleOnRequest(req);
							break;
						}
						case REQUEST_TURN_OFF: {
							rsp = handleOffRequest(req);
							break;
						}
						default: {
							rsp = handleUnexpectedRequest(req);
							break;
						}
					}
					break;
				}
				default: {
					logger.error("Received invalid namespace: " + req.getHeader().getNamespace());
					rsp = handleUnexpectedRequest(req);
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("Unexpected Exception", ex);
			rsp = handleException(req);
		}
		
		try {
	        String strRsp = gson.toJson(rsp, Response.class);
	        logger.debug("Json rsp: " + strRsp);

	        output.write(strRsp.getBytes());
	        output.close();
		} catch (IOException ioex) {
			logger.error("Unable to perform IO operation", ioex);
		}
		
		return;
    }
	
	// discover/connect to the device on the cloud
	protected Response handleDiscovery(Request req) {
		Response rsp = null;
		logger.debug("Received " + REQUEST_DISCOVER);

		if (req.getHeader().getName().contentEquals(REQUEST_DISCOVER)) {
			rsp = new Response();
			rsp.setHeader(req.getHeader());
			rsp.getHeader().setName(RESPONSE_DISCOVER);

	        ResponsePayload rspPayload = new ResponsePayload();
	        rsp.setPayload(rspPayload);
	        
	        rspPayload.setDiscoveredAppliances();
		} else {
			logger.error("But with an invalid header name: " + req.getHeader().getName());
			rsp = handleUnexpectedRequest(req);
		}
		
		return rsp;
	}
	
	protected Response handleOnRequest(Request req) {
		logger.debug("Received " + REQUEST_TURN_ON);
		Response rsp = new Response();
		rsp.setHeader(req.getHeader());
		rsp.getHeader().setName(RESPONSE_TURN_ON);
		
		SSLClientApplication client = new SSLClientApplication();
		try {
			client.sendPost(CloudUrl.TURN_ON, req.getPayload().getAccessToken());
		} catch (Exception ex) {
			logger.error("Exception", ex);
			rsp = handleException(req);
		}
		
		return rsp;
	}
	
	protected Response handleOffRequest(Request req) {
		logger.debug("Received " + REQUEST_TURN_OFF);
		
		Response rsp = new Response();
		rsp.setHeader(req.getHeader());
		rsp.getHeader().setName(RESPONSE_TURN_OFF);
		
		SSLClientApplication client = new SSLClientApplication();
		try {
			client.sendPost(CloudUrl.TURN_OFF, req.getPayload().getAccessToken());
		} catch (Exception ex) {
			logger.error("Exception", ex);
			rsp = handleException(req);
		}
		
		return rsp;
	}
	
	protected Response handleUnexpectedRequest(Request req) {
		logger.debug("Return unexpected info to request");
		Response rsp = new Response();
		rsp.setHeader(req.getHeader());
		rsp.getHeader().setName(ERROR_UNEXPECTED_INFO);
		//empty payload

		return rsp;
	}
	
	protected Response handleException(Request req) {
		logger.debug("Return unexpected info to request");
		Response rsp = new Response();
		rsp.setHeader(req.getHeader());
		rsp.getHeader().setName(ERROR_UNEXPECTED_EXCEPTION);
		//empty payload

		return rsp;
	}
}
