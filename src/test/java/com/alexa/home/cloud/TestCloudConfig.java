package com.alexa.home.cloud;

import org.junit.Test;

import com.alexa.home.cloud.config.CouldConfig;

public class TestCloudConfig {
	@Test
	public void loadCloudConfig() {
		String keystore = CouldConfig.getCouldConfiguration().getKeyStore();	
	}
}
