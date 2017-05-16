package org.shkim.socket.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NBTcpConnectionWrap implements Runnable
{
	private Socket					socket	= null;
	private final SimpleDateFormat	sdf		= new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]");

	public NBTcpConnectionWrap(Socket socket)
	{
		this.socket = socket;
	}

	public void run()
	{
		try
		{
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream(), 256);
			bos.write(sdf.format(new Date()).getBytes());
			bos.close();
		} catch (IOException e)
		{
			System.err.println(e);
		} finally
		{
			try
			{
				socket.close();
			} catch (IOException e)
			{
				System.err.println(e);
			} // try
		} // try
	}// run
}
