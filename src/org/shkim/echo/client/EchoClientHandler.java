package org.shkim.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter
{
	private final ByteBuf msg;

	public EchoClientHandler()
	{
		msg = Unpooled.buffer(EchoClient.MESSAGE_SIZE);

		byte[] str = "abcefg".getBytes();
		msg.writeBytes(str);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		ctx.writeAndFlush(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf read_msg = (ByteBuf) msg;
		int size = read_msg.readableBytes();
		byte byte_msg[] = new byte[size];
		for (int i = 0; i < size; i++)
		{
			byte_msg[i] = read_msg.getByte(i);
		}
		
		String str = new String(byte_msg);
		
		System.out.println(str);
		ctx.close();
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}
	
}
