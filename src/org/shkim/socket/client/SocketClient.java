package org.shkim.socket.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.shkim.conf.Configure;

public class SocketClient
{
	private String	host;
	private int		port;

	public SocketClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	String getTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]");
		return sdf.format(new Date());
	}

	public void run()
	{
		Socket s = null;
		try
		{
			System.out.println("Socket initialize host : " + host + ", port : " + port + " time at - " + getTime());
			s = new Socket(host, port);
			System.out.println("Socket initialize complete");

			BufferedInputStream bis = new BufferedInputStream(s.getInputStream(), 256);
			DataInputStream dis = new DataInputStream(bis);

			System.out.println("server message : "+dis.readUTF());

			dis.close();
			bis.close();
			s.close();

		} catch (Exception e)
		{
			System.err.println(e);
		}

	}

	public static void main(String[] args)
	{
		String host = Configure.SERVER_IP;
		int port = Configure.SERVER_PORT;
		new SocketClient(host, port).run();
	}
}
