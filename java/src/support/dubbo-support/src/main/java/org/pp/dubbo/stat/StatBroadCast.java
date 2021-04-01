package org.pp.dubbo.stat;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.log4j.Logger;
import com.sequoiacap.config.ConfigValue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.TaskScheduler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class StatBroadCast
	implements ApplicationListener<ContextRefreshedEvent>, Runnable
{
	private static final Logger log = Logger.getLogger(StatBroadCast.class);

	private static final int capacity = 3000;
	private static StatBroadCast _inst = null;

	@Autowired
	private TaskScheduler tasksScheduler;

	@Value("stat.port:50318")
	private ConfigValue broadcastPort;

	@Value("stat.interval:200")
	private ConfigValue interval;
	
	@Autowired
	private ApplicationConfig applicationConfig;

	private volatile ArrayList<StatFrame> frames = new ArrayList<StatFrame>(capacity);
	private volatile ArrayList<StatFrame> backup = new ArrayList<StatFrame>(capacity);

	private volatile ChannelHandlerContext channel;
	
	private volatile ScheduledFuture<?> future = null;
	
	public StatBroadCast()
	{
		_inst = this;
	}
	
	public synchronized void addFrame(StatFrame frame)
	{
		if (frames.size() >= capacity - 1)
			return;

		frames.add(frame);
	}

	public synchronized void switchs()
	{
		ArrayList<StatFrame> tmp = frames;

		frames = backup;
		backup = tmp;
		
		frames.clear();
	}

	@Override
	public void run()
	{
		if (channel == null)
			return;

		switchs();
		
		InetSocketAddress addr =
			new InetSocketAddress("255.255.255.255", broadcastPort.getInteger());
		
		ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(1024);
		
		try
		{
			StatReport report = StatReport.valueOf(applicationConfig);

			byteBuf.writerIndex(0);

			byteBuf.writeShort(StatReport.magicCode);
			byteBuf.writeByte(0);

			String message = report.toString();
			
			byte[] body = message.getBytes("utf-8");

			byteBuf.writeShort(body.length);
			byteBuf.writeBytes(body);

			channel.write(
				new DatagramPacket(Unpooled.copiedBuffer(byteBuf), addr));
		} catch(Exception e)
		{
			log.error("", e);
		}
		
		for(StatFrame frame: frames)
		{
			try
			{
				byteBuf.writerIndex(0);

				byteBuf.writeShort(StatReport.magicCode);
				byteBuf.writeByte(1);
	
				String message = frame.toString();
				
				byte[] body = message.getBytes("utf-8");
	
				byteBuf.writeShort(body.length);
				byteBuf.writeBytes(body);

				channel.write(
					new DatagramPacket(Unpooled.copiedBuffer(byteBuf), addr));
			} catch(Exception e)
			{
				log.error("", e);
			}
		}
		
		channel.flush();
		
		byteBuf.release();
		
		frames.clear();
	}

	public static StatBroadCast instance()
	{
		return _inst;
	}

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (future != null)
			return;

		future = tasksScheduler.scheduleWithFixedDelay(
			this, Duration.ofMillis(interval.getLong()));
		
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		
		Bootstrap bootstrap = new Bootstrap()
				.group(eventLoopGroup)
				.channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, true)
				.handler(new ChannelInboundHandlerAdapter() {
					@Override
				    public void channelActive(ChannelHandlerContext ctx)
				    	throws Exception
					{
				        super.channelActive(ctx);

				        channel = ctx;
				    }

				    @Override
				    public void channelInactive(ChannelHandlerContext ctx)
				    	throws Exception
				    {
				        super.channelInactive(ctx);

				        channel = null;
				    }
				});

		try
		{
			bootstrap.bind(0).sync()
				.channel().closeFuture();
		} catch(Exception e)
		{
			log.error("", e);
		}
	}
}
