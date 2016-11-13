package com.alexa.home.cloud;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;

import com.alexa.home.cloud.common.ResourceHandler;
import com.alexa.home.cloud.config.CouldConfig;
import com.google.gson.Gson;

public class SSLClientApplication extends Application implements AutoCloseable {
	public static final Logger logger = Logger.getLogger(SSLClientApplication.class.getCanonicalName());
	
	private final String KEY_STORE = CouldConfig.getCouldConfiguration().getKeyStore();
	private final String KEY_PASSWD = CouldConfig.getCouldConfiguration().getKeyPasswd();
	private final String KEYSTORE_TYPE = CouldConfig.getCouldConfiguration().getStoreType();
	private final String SSL_PROTO = CouldConfig.getCouldConfiguration().getCloudProto();
	
	private String keyStorePath = null;
	
	   static {
	        //for localhost testing only
	        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	        new javax.net.ssl.HostnameVerifier(){

	            public boolean verify(String hostname,
	                    javax.net.ssl.SSLSession sslSession) {
	                if (hostname.equals("localhost")) {
	                    return true;
	                }
	                return false;
	            }
	        });
	    }
	
	public void close() {
		try {
			new File(keyStorePath).delete();
		} catch (Exception ex) {
		    logger.error("Cannot delete file: " + keyStorePath, ex);
		}
	}
	
	private Context getClientContext() throws Exception {	
		final Context context = new Context();
        Series<Parameter> parameters = context.getParameters();
        parameters.add("sslContextFactory", "org.restlet.engine.ssl.DefaultSslContextFactory");
        
        keyStorePath = ResourceHandler.exportResource(KEY_STORE);
        
        parameters.add("keyStorePath", keyStorePath);
        parameters.add("keyStorePassword", KEY_PASSWD);
        parameters.add("keyPassword", KEY_PASSWD);
        parameters.add("keyStoreType", KEYSTORE_TYPE);
        parameters.add("protocol", SSL_PROTO);
        
        System.setProperty("javax.net.ssl.trustStore", keyStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", KEY_PASSWD); 
        
        return context;
	}
	
	public Representation sendPost(String requestType, String token) throws Exception {
		ClientResource resource = null;
		Representation rsp = null;
		CloudUrl url = new CloudUrl();
		
		switch (requestType) {
		    case CloudUrl.DISCOVER_URL: {
	            resource = new ClientResource(getClientContext(),
	                     url.getDiscoiverUrl());
		        break;
		    }
			case CloudUrl.TURN_ON: {
				resource = new ClientResource(getClientContext(),
						url.getTurnOnUrl());
				break;
			}
			case CloudUrl.TURN_OFF: {
				resource = new ClientResource(getClientContext(),
						url.getTurnOffUrl());
				break;
			}
			default:
				throw new MalformedURLException("Wrong requestType: " + requestType);
		}
		resource.setMethod(Method.POST);
		
		ApplicationRequest applRequest = new ApplicationRequest();
		applRequest.setAccessToken(token);
		
		Gson gson = new Gson();
		StringRepresentation stringRep = new StringRepresentation(gson.toJson(applRequest, ApplicationRequest.class).toString());
		stringRep.setMediaType(MediaType.APPLICATION_JSON);
		
		try {
			logger.info("Sending request to: " + resource.getOriginalRef());
			rsp = resource.post(stringRep);
			logger.info("Sent successfully");
		} catch (ResourceException rsex) {
			logger.error("ResourceException", rsex);
			throw new Exception(rsex);
		}
		
		return rsp;
	}
}
