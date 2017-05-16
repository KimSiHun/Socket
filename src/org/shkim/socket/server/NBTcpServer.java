package org.shkim.socket.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.shkim.conf.Configure;

public class NBTcpServer
{
	private int port;
	private int max_user;
	
	public NBTcpServer(int port, int max_user)
	{
		this.port = port;
		this.max_user = max_user;
	}

	private void run()
	{
		ServerSocket server = null;
		try{
			server = new ServerSocket(port);
			ExecutorService thread_pool = Executors.newFixedThreadPool(max_user);

			while(true){
				Socket socket =server.accept();
				try{
					thread_pool.execute(new NBTcpConnectionWrap(socket));
				}catch(Exception e){
					System.err.println(e);
				}// end try
			}//end while
		}catch(Exception e){
			System.err.println(e);
		}//try
	}//run
	
	public static void main(String[] args)
	{
		int port = Configure.SERVER_PORT;
		int max_user = Configure.MAX_USER;
		
		new NBTcpServer(port, max_user).run();
		
	}
}
