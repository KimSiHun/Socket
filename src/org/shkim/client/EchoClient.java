package org.shkim.client;

import org.shkim.conf.Configure;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient
{
	private String			host;
	private int				port;

	public static final int	MESSAGE_SIZE	= 256;

	public EchoClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void run()
	{
		EventLoopGroup group = new NioEventLoopGroup();

		try
		{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>()
					{
						@Override
						protected void initChannel(SocketChannel ch) throws Exception
						{
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});

			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e)
		{
			System.err.println(e);
		} finally
		{
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args)
	{
		String host = Configure.SERVER_IP;
		int port = Configure.SERVER_PORT;
		
		new EchoClient(host, port).run();

	}
}
