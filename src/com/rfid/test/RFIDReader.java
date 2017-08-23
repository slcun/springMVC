package com.rfid.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.rfid.util.SocketUtil;

public class RFIDReader {

	private static final Logger log = Logger.getLogger(RFIDReader.class);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		SocketService socket = new SocketService();
		socket.connect();
		
		Socket client = socket.getClient();
		InputStream input = socket.getInput();
		OutputStream output = socket.getOutput();
		

		while(true) {
			try {
				client = socket.getClient();
				input = socket.getInput();
				output = socket.getOutput();
				
				log.info("发送");
				output.write(SocketUtil.hexStringToBytes("BB000300171E380D0A"));
				
				byte[] b = new byte[8];
				
				int len;
				while((len=input.read(b)) != -1) {
					log.info("读取");
					System.out.println(SocketUtil.bytesToHexString(b));
					Thread.sleep(2000);
				}
			} catch (Exception e) {
				log.info("链接出错,停止io流");
			}
			
			Thread.sleep(2000);
			
			
		}
		
	}
	
	
	
	
	
	
}
