package com.scdt.shorturl.generator;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Snowflake算法改进版
 * @author 周小建
 */
public class SnowflakeGenerator{
	private static final ConcurrentHashMap<String,SnowflakeGenerator>generatorMap=new ConcurrentHashMap<String,SnowflakeGenerator>();//对象缓存池，避免重复创建。
	private static volatile Random random=new Random();//随机生成器
	
	private Long serviceIdBits=8L;//业务线标识id所占的位数
	private Long maxServiceId=-1L^(-1L<<serviceIdBits);//业务线标识支持的最大数据标识id(这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
	private Long serviceId;

	private Long workerIdBits=10L;//机器id所占的位数
	private Long maxWorkerId=-1L^(-1L<<workerIdBits);//支持的最大机器id
	private Long workerId;

	private Long sequenceBits=7L;//序列在id中占的位数
	private Long sequenceMask=-1L^(-1L<<sequenceBits);

	private Long twepoch=1514736000000L;//开始时间戳（2018年1月1日）
	private volatile Long lastTimestamp=-1L;//最后一次的时间戳
	private volatile Long sequence=0L;//毫秒内序列
	
	private Long workerIdShift=sequenceBits;//机器id左移位数
	private Long serviceIdShift=workerIdBits+sequenceBits;//业务线id左移位数
	private Long timestampLeftShift=serviceIdBits+workerIdBits+sequenceBits;//时间戳左移位数

	/**
	 * 构造方法
	 * @author 周小建
	 * @param serviceIdBits
	 * @param workerIdBits
	 * @param sequenceBits
	 * @param twepoch
	 * @param workerId
	 * @param serviceId
	 */
	private SnowflakeGenerator(Long serviceIdBits,Long workerIdBits,Long sequenceBits,Number twepoch,Long workerId,Long serviceId){
		init(serviceIdBits,workerIdBits,sequenceBits,twepoch);
		init(workerId,serviceId);
	}
	/*
	private SnowflakeGenerator(Long serviceIdBits,Long workerIdBits,Long sequenceBits,Number twepoch){
		init(serviceIdBits,workerIdBits,sequenceBits,twepoch);
	}
	private SnowflakeGenerator(Long workerId,Long serviceId){
		init(workerId,serviceId);
	}
	private SnowflakeGenerator(Long serviceId){
		init(null,serviceId);
	}
	*/
	
	/**
	 * 初始化
	 * @author 周小建
	 * @param serviceIdBits
	 * @param workerIdBits
	 * @param sequenceBits
	 * @param twepoch
	 */
	private void init(Long serviceIdBits,Long workerIdBits,Long sequenceBits,Number twepoch){
		//if(serviceIdBits!=null)
		this.serviceIdBits=serviceIdBits;
		this.maxServiceId=-1L^(-1L<<this.serviceIdBits);
		//if(workerIdBits!=null)
		this.workerIdBits=workerIdBits;
		this.maxWorkerId=-1L^(-1L<<this.workerIdBits);
		//if(sequenceBits!=null)
		this.sequenceBits=sequenceBits;
		this.sequenceMask=-1L^(-1L<<this.sequenceBits);
		/*
		if(twepoch!=null){
			if(twepoch instanceof Integer)twepoch=new Date(((Integer)twepoch)-1900,0,1).getTime();//1609430400000
			this.twepoch=(Long)twepoch;
		}
		*/
		this.twepoch=(Long)twepoch;
		this.workerIdShift=this.sequenceBits;
		this.serviceIdShift=this.workerIdBits+this.sequenceBits;
		this.timestampLeftShift=this.serviceIdBits+this.workerIdBits+this.sequenceBits;
	}
	
	/**
	 * 初始化
	 * @author 周小建
	 * @param workerId
	 * @param serviceId
	 */
	private void init(Long workerId,Long serviceId){
		//if(workerId==null)workerId=getWorkerId();
		if((workerId>maxWorkerId)||(workerId<0))throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",maxWorkerId));
		this.workerId=workerId;
		if((serviceId>maxServiceId)||(serviceId<0))throw new IllegalArgumentException(String.format("service Id can't be greater than %d or less than 0",maxServiceId));
		this.serviceId=serviceId;
	}
	
	/**
	 * 获取实例
	 * @author 周小建
	 * @param serviceIdBits
	 * @param workerIdBits
	 * @param sequenceBits
	 * @param twepoch
	 * @param workerId
	 * @param serviceId
	 * @return SnowflakeGenerator
	 */
	public static SnowflakeGenerator getInstance(Long serviceIdBits,Long workerIdBits,Long sequenceBits,Number twepoch,Long workerId,Long serviceId){
		String key=workerId+","+serviceId;
		SnowflakeGenerator generator=generatorMap.get(key);
		if(generator==null){
			generator=new SnowflakeGenerator(serviceIdBits,workerIdBits,sequenceBits,twepoch,workerId,serviceId);
			//SnowflakeGenerator previousGenerator=generatorMap.putIfAbsent(key,generator);//putIfAbsent解决多线程并发写入问题
			//if(previousGenerator!=null)generator=previousGenerator;
			generatorMap.put(key,generator);
		}
		return generator;
	}
	
	/**
	 * 等待下一个毫秒的到来，保证返回的毫秒数在参数lastTimestamp之后不停获得时间，直到大于最后时间
	 * @author 周小建
	 * @param lastTimestamp
	 * @return Long
	 */
	private Long tilNextMillis(final long lastTimestamp){
		Long timestamp=System.currentTimeMillis();
		while(timestamp<=lastTimestamp)timestamp=System.currentTimeMillis();
		return timestamp;
	}
	
	/**
	 * 获取下一个值
	 * @author 周小建
	 * @return Long
	 * @throws Exception
	 */
	private synchronized Long nextId()throws Exception{
		long timestamp=System.currentTimeMillis();
		if(timestamp<lastTimestamp)throw new Exception("Clock moved backwards.  Refusing to generate id for "+(lastTimestamp-timestamp)+" milliseconds.");
		//如果是同一时间生成的，则进行毫秒内序列
		if(lastTimestamp==timestamp){
			sequence=(sequence+1)&sequenceMask;
			if(sequence==0)timestamp=tilNextMillis(lastTimestamp);
		}else sequence=random.nextInt(10)&sequenceMask;//跨毫秒时，序列号总是归0，会导致序列号为0的ID比较多，导致生成的ID取模后不均匀，所以采用10以内的随机数
		lastTimestamp=timestamp;//上次生成ID的时间截（设置最后时间戳）
		//移位并通过或运算拼到一起组成64位的ID
		return ((timestamp-twepoch)<<timestampLeftShift) //时间戳
				|(serviceId<<serviceIdShift) //业务线
				|(workerId<<workerIdShift) //机器
				|sequence; //序号
	}
	
	/*
	@Override
	public String generate(String url){
		try{
			return nextId().toString();
		}catch(Exception e){
			return "";
		}
	}
	*/
	/**
	 * 生成下一个值，提供给外部调用的主接口。
	 * @author 周小建
	 * @param url
	 * @return Long
	 */
	public Long generate(String url){
		try{
			return nextId();
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 根据机器的MAC地址获取工作进程Id，也可以使用机器IP获取工作进程Id，取最后两个段，一共10个bit
	 * 极端情况下，MAC地址后两个段一样，产品的工作进程Id会一样；再极端情况下，并发不大时，刚好跨毫秒，又刚好随机出来的sequence一样的话，产品的Id会重复
	 * @return
	 * @throws IdsException
	 */
	/*
	private long getWorkerId(){
		try{
			java.util.Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
			while(en.hasMoreElements()){
				NetworkInterface iface=en.nextElement();
				List<InterfaceAddress>addrs=iface.getInterfaceAddresses();
				for(InterfaceAddress addr:addrs){
					InetAddress ip=addr.getAddress();
					NetworkInterface network=NetworkInterface.getByInetAddress(ip);
					if(network==null)continue;
					byte[]mac=network.getHardwareAddress();
					if(mac==null)continue;
					long id=((0x000000FF&(long)mac[mac.length-1])|(0x0000FF00&(((long)mac[mac.length-2])<<serviceIdBits)))>>11;
					if(id>maxWorkerId)return new Random(maxWorkerId).nextInt();
					return id;
				}
			}
			return new Random(maxWorkerId).nextInt();
		}catch(SocketException e){
			//throw new Exception(e);
			throw new IllegalArgumentException("worker Id invalid");
		}
	}

	private static Long getService(Long id){//获取业务线
		String str=Long.toBinaryString(id);
		int size=str.length();
		String sequenceBinary=str.substring(size-7-10-8,size-7-10);
		return Long.parseLong(sequenceBinary,2);
	}
	
	private static Long getWorker(Long id){//获取机器
		String str=Long.toBinaryString(id);
		int size=str.length();
		String sequenceBinary=str.substring(size-7-10,size-7);
		return Long.parseLong(sequenceBinary,2);
	}
	
	private Long getSequence(Long id){//获取序号
		String str=Long.toBinaryString(id);
		int size=str.length();
		String sequenceBinary=str.substring(size-7,size);
		return Long.parseLong(sequenceBinary,2);
	}
	*/
	
	/*
	public static void main(String[]args)throws Exception{
		SnowflakeGenerator snowFlakeGenerator=new SnowflakeGenerator(2L,2L,7L,1617206400000L,0L,0L);
		for(int i=0;i<(1<<4);i++){
			Long id=snowFlakeGenerator.nextId();//10进制
			System.out.println("序号："+id);
			String convertedNumStr=NumericConvertUtil.toOtherNumber(id,62);//62进制
			System.out.println("10进制："+id+"  62进制:"+convertedNumStr);//10进制转化为62进制
			//TODO 执行具体的存储操作，可以存放在Redis等中
			//62进制转化为10进制
			System.out.println("62进制："+convertedNumStr+"  10进制:"+NumericConvertUtil.toDecimalNumber(convertedNumStr,62));
			System.out.println();
		}
	}
	*/
}