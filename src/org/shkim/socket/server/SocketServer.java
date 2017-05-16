package org.shkim.socket.server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.shkim.conf.Configure;

public class SocketServer
{
	private int port;

	public SocketServer(int port)
	{
		this.port = port;
	}

	String getTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]");
		return sdf.format(new Date());
	}

	public void run()
	{
		System.out.println("Server Initialize process start.... at " + getTime());
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(port);
			if (server.isBound())
			{
				System.out.println("server initialize complete");
			} else
			{
				System.out.println("server initialize failed");
				System.exit(0);
			}

			while (true)
			{
				System.out.println("waiting connection for client");
				Socket socket = server.accept();
				System.out.println(socket.getInetAddress() + " connect time at - " + getTime());

				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream(), 256);
				DataOutputStream dos = new DataOutputStream(bos);

				dos.writeUTF("[Notice] Test from server");
				System.out.println("Message to " + socket.getInetAddress() + " time - " + getTime());
				dos.close();
				bos.close();
			}
		} catch (IOException e)
		{
			System.err.println(e);
		}
	}
	
	public static void main(String[] args)
	{
		int port = Configure.SERVER_PORT;
		new SocketServer(port).run();
	}
}
