package com.rfid.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.rfid.cfg.RFIDConfig;
import com.rfid.util.SocketUtil;

@Component
public class ScoketClient{
	private static final Logger log = Logger.getLogger(ScoketClient.class);
	/**
	 * 配置文件
	 */
	private Properties prop = new RFIDConfig().getProp();
	private String ip_addr = prop.getProperty("ip_addr");
	private Integer port = Integer.parseInt(prop.getProperty("port"));

	static HashSet<String> hashSet1;
	static HashSet<String> hashSet2;
	static Long time1;
	static Long time2;
	
	String lopCommand;
	public String getLopCommand() {
		return lopCommand;
	}
	public void setLopCommand(String lopCommand) {
		this.lopCommand = lopCommand;
	}

	public static Map<String, Set> map = new HashMap();
	public static Map<String, Set> getMap() {
		Map<String, Set> map = new HashMap();
		map.put(String.valueOf(time1), hashSet1);
		map.put(String.valueOf(time2), hashSet2);
		return map;
	}
	
	/**
	 * @return Socket
	 */
	private Socket getSocket() {
		log.info("创建Socket连接");
		Socket socket = null;
		try {
			socket = new Socket(ip_addr, port);
			log.info("创建Socket连接成功");
		} catch (IOException e) {
			log.info("找不到主机");
			e.printStackTrace();
		}
		return socket;
	}

	/**
	 * Scoket发送指令
	 * 
	 * @param Command
	 * @return 单个响应
	 * @throws IOException
	 */
	public String sendCommand(String Command) throws IOException {
		Socket socket = getSocket();
		OutputStream output = socket.getOutputStream();
		InputStream input = socket.getInputStream();
		log.info("发送指令: " + Command);
		output.write(SocketUtil.hexStringToBytes(Command));
		String response = this.getResponse(input);
		output.close();
		input.close();
		socket.close();

		return response;
	}

	public void sendLopCommand(String Command) {

		Socket socket = getSocket();
		OutputStream output = null;
		InputStream input = null;
		try {
			output = socket.getOutputStream();
			input = socket.getInputStream();
			log.info("发送指令: " + Command);
			output.write(SocketUtil.hexStringToBytes(Command));

			int i = 0;
			byte[] b = new byte[1024];
			String regx = "bb(?!bb)(\\w){6,}?0d0a";
			log.info("循环读取响应");
			while (input.read(b) != -1) {
				if (i % 2 == 0) {
//					log.info("偶数");
					time1 = System.currentTimeMillis();
					hashSet1 = SocketUtil.getString(SocketUtil.bytesToHexString(b), regx);
//					map.put(String.valueOf(time1), hashSet1)
				} else {
//					log.info("奇数");
					time2 = System.currentTimeMillis();
					hashSet2 = SocketUtil.getString(SocketUtil.bytesToHexString(b), regx);
//					map.put(String.valueOf(time2), hashSet2);
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
//	@Override
//	public void run() {
//		if (client == null || !client.isConnected() || client.isClosed()) {
//			log.info("重新链接");
//			this.connect();
//		}
//		sendLopCommand(getLopCommand());
//		System.out.println("RUN");
//	}

	/**
	 * 读取单次响应
	 * 
	 * @param input
	 * @return
	 */
	public String getResponse(InputStream input) {
		byte[] b = new byte[1024];
		log.info("读取响应");
		try {
			return SocketUtil.bytesToHexString(Arrays.copyOf(b, input.read(b)));
		} catch (IOException e) {
			log.info("读取响应失败");
			e.printStackTrace();
			return null;
		}
	}

}
