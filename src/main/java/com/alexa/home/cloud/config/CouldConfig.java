package com.alexa.home.cloud.config;

import java.io.File;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import com.alexa.home.cloud.common.ResourceHandler;

public class CouldConfig {
	private static final Logger logger = Logger.getLogger(CouldConfig.class.getCanonicalName());
	
	private static CouldConfig __instance = new CouldConfig();
	
	private String keyStore = null;
    private String keyPasswd = null;
    private String storeType = null;
    private String cloudProto = null;
	private String serverURL = null;
	
	public CouldConfig() {
		try {
			readConfigData();
		} catch (Exception ex) {
			logger.error("Exception:", ex);
		}
	}
	
	public static CouldConfig getCouldConfiguration() {
		return __instance;
	}
	
	public String getKeyStore() {
		return keyStore;
	}
	public void setKeystore(XMLConfiguration config) {
		keyStore = new String (config.getString("keystore.file", "unknown.jks"));
	}
	
	public String getKeyPasswd() {
		return keyPasswd;
	}
	public void setKeyPasswd(XMLConfiguration config) {
		keyPasswd = new String (config.getString("keystore.passwd", "setyourpasswd"));
	}
	
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(XMLConfiguration config) {
		storeType = new String (config.getString("storetype", "setyourpasswd"));
	}
	
	public String getCloudProto() {
		return cloudProto;
	}
	public void setCloudProto(XMLConfiguration config) {
		cloudProto = new String (config.getString("cloudproto", "TLS"));
	}
	
	public String getServerURL() {
		return serverURL;
	}
	public void setServerURL(XMLConfiguration config) {
		serverURL = new String (config.getString("url", "https://localhost:9443"));
	}
	
	public void readConfigData() throws Exception {
		String configFile = ResourceHandler.exportResource("CloudConfig.xml");
		
		if (configFile != null) {
			File confd = new File(configFile);
			if (confd.exists() && confd.isFile() && confd.canRead()) {
				XMLConfiguration config = new XMLConfiguration(confd);
				setKeystore(config);
				setKeyPasswd(config);
				setStoreType(config);
				setCloudProto(config);
				setServerURL(config);
			}
		}
	}
}
