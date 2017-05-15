package org.shkim.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.shkim.conf.Configure;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer
{
	private int	port;
	private int	user_max_cnt;

	public EchoServer(int port, int user_max_cnt)
	{
		this.port = port;
		this.user_max_cnt = user_max_cnt;
	}

	public void run() throws UnknownHostException
	{
		System.out.println("Server ip - " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
		EventLoopGroup boss_group = new NioEventLoopGroup(user_max_cnt);
		EventLoopGroup worker_group = new NioEventLoopGroup();
		try
		{
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(boss_group, worker_group).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>()
					{
						@Override
						protected void initChannel(SocketChannel ch) throws Exception
						{
							ch.pipeline().addLast(new EchoServerHandler());

						}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture f = sb.bind(port).sync();

			f.channel().closeFuture().sync();
		} catch (Exception e)
		{
			System.err.println(e);
		} finally
		{
			worker_group.shutdownGracefully();
			boss_group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws UnknownHostException
	{
		int port = Configure.SERVER_PORT;
		int user_max_cnt = Configure.MAX_USER;

		new EchoServer(port, user_max_cnt).run();
	}
}
