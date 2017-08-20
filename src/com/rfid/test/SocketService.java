package com.rfid.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rfid.cfg.RFIDConfig;

public class SocketService extends Thread {

	private static final Logger log = Logger.getLogger(SocketService.class);

	private Properties prop;// = RFIDConfig.prop;

	private String ip_addr = prop.getProperty("ip_addr");
	private Integer port = Integer.parseInt(prop.getProperty("port"));

	private Socket client;
	private OutputStream output;
	private InputStream input;

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public SocketService() {
	}

	/**
	 * 载入config.properties
	 * 
	 * @return Properties
	 */
	public void connect() {
		boolean flag = true;
		while (flag) {
			log.info("开始连接服务器 " + ip_addr + ":" + port);
			try {
				client = new Socket(ip_addr, port);
				input = client.getInputStream();
				output = client.getOutputStream();
				log.info("链接成功");
				if (!this.isAlive()) {
					log.info("启动监控线程");
					this.setDaemon(true);
					this.start();
					this.setName("后台监控线程");
				}
				flag = false;
			} catch (IOException e) {
				log.info("链接失败");
				client = null;
				e.printStackTrace();
			} finally {

			}
		}

	}

	@Override
	public void run() {
		super.run();
		while (true) {
			if (client == null || !client.isConnected() || client.isClosed()) {
				log.info("重新链接");
				this.connect();
			}
			boolean connectStatus = true;
			while (connectStatus) {
				try {
					log.info("检查链接");
					client.sendUrgentData(0xFF);
				} catch (IOException e) {
					log.info("链接已断开");
					e.printStackTrace();
					client = null;
					connectStatus = false;
					continue;
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
