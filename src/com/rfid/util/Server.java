package com.rfid.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 */
public class Server {
	public static void main(String[] args) {
		ServerSocket server =null;
		try {
			boolean f = true;
			
			server = new ServerSocket(9001);
			
			while(f) {
				
				//等待接收数据
				Socket accept = server.accept();
				
				InputStream is = accept.getInputStream();
				OutputStream os = accept.getOutputStream();
				
				byte[] bytes = SocketUtil.hexStringToBytes("BB9711A33000112233445566778899001122FD6F031A0D0ABB9711A33000112233445566777799001122FD6F031A0D0A");
				
				if(is.read() > 0) {
					for( ; ;) {
						os.write(bytes);
					}
				}
				
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				is.close();
				os.close();
//				accept.shutdownInput();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(server!=null){
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		
		
	}
}
