package com.alexa.home.cloud.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.alexa.home.cloud.SSLClientApplication;

public class ResourceHandler {
	public static final Logger logger = Logger.getLogger(ResourceHandler.class.getCanonicalName());
	
	public static String exportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String expResourceFile;
        try {
            stream = SSLClientApplication.class.getClassLoader().getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            
            String myPath = System.getProperty("java.io.tmpdir") ;
            logger.info("java.io.tmpdir: " + myPath);
            
            int readBytes;
            byte[] buffer = new byte[4096];
            expResourceFile = new File(myPath.concat("/").concat(resourceName)).getPath().replace('\\', '/');
            
            logger.info("New file: " + expResourceFile);
            
            resStreamOut = new FileOutputStream(expResourceFile);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
        	logger.error("Exception", ex);
            throw new Exception(ex);
        } finally {
            if (stream != null) 
            	stream.close();
            if (resStreamOut != null)
            	resStreamOut.close();
        }

        return expResourceFile;
    }
}
