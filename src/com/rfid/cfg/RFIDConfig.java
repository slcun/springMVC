package com.rfid.cfg;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

public class RFIDConfig {

	private static final Logger log = Logger.getLogger(RFIDConfig.class);

	private Properties prop;
	/**
	 * @return the prop
	 */
	public Properties getProp() {
		return prop;
	}
	
	public RFIDConfig(){
		lodCfg();
	}

	private Properties lodCfg() {
		prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/config.properties");
		try {
			log.info("加载config.properties");
			prop.load(in);
			in.close();
			log.info("加载config.properties成功");
		} catch (IOException e) {
			log.info("加载config.properties出错");
			e.printStackTrace();
		}
		return prop;
	}
	
	@Test
	public void testName() throws Exception {
		lodCfg();
		Iterator it=prop.entrySet().iterator();
		while(it.hasNext()){
		    Map.Entry entry=(Map.Entry)it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key +":"+value);
		}
	}

}
