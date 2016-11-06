package com.alexa.home.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import com.alexa.home.cloud.common.ResourceHandler;

public class TestCA {
	
	public void test(String resourceName) throws FileNotFoundException, Exception {
		String tmpName = ResourceHandler.exportResource(resourceName);
		
		// load jks into keystore
		KeyStore caKs = KeyStore.getInstance("PKCS12");
		String caPassword = "home12auto34";
		String caAlias = "dansirbu.myasustor.com";
		caKs.load(new FileInputStream(new File(tmpName)), caPassword.toCharArray());
		
		// load the key entry from the keystore
        // RSAPrivateCrtKey privKey = (RSAPrivateCrtKey) key;
        Key key = caKs.getKey(caAlias, caPassword.toCharArray());
        if (key == null) {
            throw new RuntimeException("Got null key from keystore!"); 
        }
		

        //caPrivateKey = new RSAPrivateCrtKeyParameters(privKey.getModulus(), privKey.getPublicExponent(), privKey.getPrivateExponent(),
        //        privKey.getPrimeP(), privKey.getPrimeQ(), privKey.getPrimeExponentP(), privKey.getPrimeExponentQ(), privKey.getCrtCoefficient());
        
        // and get the certificate
        X509Certificate caCert = (X509Certificate) caKs.getCertificate(caAlias);
        if (caCert == null) {
            throw new RuntimeException("Got null cert from keystore!"); 
        }
        
        caCert.verify(caCert.getPublicKey());
        caKs.setCertificateEntry(caAlias, caCert);
	}
	
	public static boolean pingHost(String host, int port, int timeout) throws IOException {
	    Socket socket = new Socket();
	    socket.connect(new InetSocketAddress(host, port), timeout);
	    socket.close();
	    return true;
	}
}
