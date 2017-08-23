/**
 * 
 */
package com.rfid.app;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rfid.api.RFIDApi;
import com.rfid.cfg.RFIDConfig;
import com.rfid.socket.ScoketClient;

/**
 * 
 * @author Dolphin-PC
 * 2017年8月17日
 */
public class RFIDApp implements Runnable{
	
	private static final Logger log = Logger.getLogger(RFIDApp.class);

	public static void main(String[] args) {
		RFIDApi api = new RFIDApi();
		ScoketClient scoketClient = api.getScoketClient();
//		Boolean isOK = api.setAnt("0A");
//		System.out.println(isOK);
		
		
		
		try {
			api.lopQueryEPC("00", "00");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(1==1) {
			log.info("打印Map");
			System.out.println(scoketClient.getMap());
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
