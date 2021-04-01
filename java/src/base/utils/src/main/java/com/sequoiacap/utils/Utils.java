package com.sequoiacap.utils;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

public class Utils
{
    public final static Timestamp MIN_TIMESTAMP = Timestamp.valueOf("0000-1-1 0:0:0.0");
    public final static Timestamp MAX_TIMESTAMP = Timestamp.valueOf("9999-12-31 23:59:59.999");

    public final static long SECONDS = 1 * 1000l;
    public final static long MINUTE = 60 * 1000l;
    public final static long HOUR = 60 * MINUTE;
    public final static long DAY = 24 * HOUR;

    public static Timestamp zeroDate()
    {
    	return new Timestamp(0l);
    }

    public static Timestamp now()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp nowInSec()
    {
    	Long currentTime = System.currentTimeMillis() / 1000l;
    	
    	return new Timestamp(currentTime * 1000l);
    }

    public static Timestamp forever()
    {
    	return new Timestamp(253402271999000l);
    }

    public static Timestamp today()
    {
    	Timestamp now = now();

    	Date today = new Date(now.getYear(), now.getMonth(), now.getDate());

    	return new Timestamp(today.getTime());
    }

    public static Timestamp offsetTimestamp(Timestamp from, long offset)
    {
    	return new Timestamp(from.getTime() + offset);
    }

    public static Timestamp offsetTimestamp(Timestamp from, int offset, int unit)
    {
    	Calendar calendar = Calendar.getInstance();

    	calendar.setTime(from);
    	calendar.add(unit, offset);

    	return new Timestamp(calendar.getTime().getTime());
    }
    
    public static long diffTimestamp(Timestamp left, Timestamp right)
    {
    	return left.getTime() - right.getTime();
    }
    
    public static String randomUuid()
    {
    	UUID uuid = UUID.randomUUID();
    	
    	String random =
    		String.format("%016x%016x",
    			uuid.getLeastSignificantBits(),
    			uuid.getMostSignificantBits());

    	return random;
    }

    public static String randomId()
    {
        String id = String.format("%04x",
            (int) (Math.random() * 65535));

        return id;
    }
    
    public static String randomId(String prefix)
    {
        if (prefix == null)
            prefix = "tag";
        
        String id = String.format("%s-%x", prefix,
            (int) (Math.random() * Integer.MAX_VALUE));
        
        return id;
    }

    public static String randomStr()
    {
        StringBuilder builder = new StringBuilder();

        int count = 4;
        while(count-- != 0)
        {
        	builder.append(randomId());
        }

        return builder.toString();
    }
    
    public static String getRandomStringByLength(int length)
    {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        
        for(int i = 0; i < length; i++)
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }
    
    static char[] strbase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    
    public static String getRandomStringByLength(int length, Random random, char[] sb)
    {
        if (random == null)
        	random = new Random();
        
        if (sb == null)
        	sb = new char[length];
        
        for(int i = 0; i < length; i++)
        {
        	int number = random.nextInt(strbase.length);
        	
        	sb[i] = strbase[number];
        }

        return new String(sb);
    }

    public static Double keepScale(Double value, int scale)
    {
        BigDecimal big = new BigDecimal(value); 
        
        big.setScale(scale, BigDecimal.ROUND_HALF_UP);
        value = big.doubleValue();
        
        return value;
    }

    public static int betweenDays(Date left, Date right)
    {
    	Date leftDate = thatDay(left);
    	Date rightDate = thatDay(right);

    	long diff = Math.abs(leftDate.getTime() - rightDate.getTime());
    	
    	int days =(int) (diff / (1000 * 60 * 60 * 24));

    	return days;
    }

    public static int betweenDaysNoAbs(Date left, Date right)
    {
    	Date leftDate = thatDay(left);
    	Date rightDate = thatDay(right);

    	long diff = leftDate.getTime() - rightDate.getTime();
    	
    	int days =(int) (diff / (1000 * 60 * 60 * 24));

    	return days;
    }
    
    public static Timestamp thatDay(Date time)
    {
    	return new Timestamp(
    		time.getYear(), time.getMonth(), time.getDate(), 0, 0, 0, 0);
    }
    
    public static Timestamp thatDayEnd(Date time)
    {
    	return new Timestamp(
    		time.getYear(), time.getMonth(), time.getDate(), 23, 59, 59, 59);
    }
    
    public static String formatDate(String format, Date date)
    {
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	
    	return formatter.format(date);
    }

    /**
     * @param dateType yyyyMMdd(20150504)  yyyyMMddHHmmss(20150504121744)
     * @param num 中间随机数位数  最大19位
     * @param id 相关ID（取5位，不足前补0；超出5位，截取后5位）
     * @return
     */
    public static String code(String dateType, Integer num, Integer id)
    {
    	if (dateType == null)
    		dateType = "yyyyMMddHHmmss";
    	
    	if (num == null)
    		num = 4;
    	
    	SimpleDateFormat from = new SimpleDateFormat(dateType);
    	String time=from.format(new Date());
    	
        String mid = String.format("%0" + num + "d",
            (long) (Math.random() * (Math.pow(10, num) - 1)));

        String idStr = (id != null)? String.format("%05d", id): "";

    	return time + mid + idStr;
    }

    /**
     * 生成单号  规则yyyyMMddHHmmss+6位随机数
     * 例：20151121131315250488
     * @return
     */
    public static String num()
    {
    	SimpleDateFormat from = new SimpleDateFormat("yyyyMMddHHmmss");
    	String time=from.format(new Date());
    	
        long id = (long) (Math.random() * (Math.pow(10,6) - 1));
    	
        String idStr = String.format("%06d", id);
        
    	return time + idStr;
    }

    public static long longOf(Object value)
    {
    	if (value == null || !(value instanceof Number))
    		return 0l;

    	return ((Number) value).longValue();
    }

    public static String dumpMethod(Object ... args)
    {
    	StringBuilder builder = new StringBuilder();

    	String method =
    		Thread.currentThread().getStackTrace()[2].getMethodName();

    	builder.append(method);

    	builder.append("##parameter[");
    	for(int index = 0; index != args.length; ++index)
    	{
    		builder.append(args[index]);
    		
    		builder.append((index % 2 == 1)? "=": ",");
    	}

    	builder.append("]");

    	return builder.toString();
    }

    public static Method getCurrentMethod(Class<?> clazz)
    {
    	try
    	{
    		StackTraceElement[] stack = new Throwable().getStackTrace();
    		
    		Method method = clazz.getMethod(stack[1].getMethodName());
    	} catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return null;
    }

    public static <S, T> Collection<T> map(
		S[] source, Collection<T> target,
    	Transformer transform)
    {
    	for(S s: source)
    	{
    		target.add((T) transform.transform(s));
    	}

    	return target;
    }
    
    public static <S, T> Collection<T> map(
		Iterable<S> source, Collection<T> target,
    	Transformer transform)
    {
    	for(S s: source)
    	{
    		target.add((T) transform.transform(s));
    	}

    	return target;
    }
    
    public static <S, T> T[] map(
    	Collection<S> source, Class<T> clazz, Transformer transform)
    {
    	int index = 0, length = source.size();
    	T[] result = null;
    	
    	for(S s: source)
    	{
    		T r =(T) transform.transform(s);
    		
    		if (result == null)
    			result =(T[]) Array.newInstance(r.getClass(), length);

    		if (index < length)
    			result[index++] = r;
    	}

    	return result;
    }

    public static <S, T> T[] map(S[] source, Class<T> clazz, Transformer transform)
    {
    	int index = 0, length = source.length;
    	T[] result = null;
    	
    	for(S s: source)
    	{
    		T r =(T) transform.transform(s);
    		
    		if (result == null)
    			result =(T[]) Array.newInstance(r.getClass(), length);

    		if (index < length)
    			result[index++] = r;
    	}

    	return result;
    }

    public static <E> String joinStr(E[] it, String ch)
    {
    	StringBuilder builder = new StringBuilder();
    	
    	if (it != null)
    	{
	    	for(E e: it)
	    	{
	    		if (builder.length() != 0)
	    			builder.append(ch);
	    		
	    		builder.append(String.valueOf(e));
	    	}
    	}
    	
    	return builder.toString();
    }

    public static <E> String joinStr(Iterable<E> it, String ch)
    {
    	StringBuilder builder = new StringBuilder();
    	
    	if (it != null)
    	{
	    	for(E e: it)
	    	{
	    		if (builder.length() != 0)
	    			builder.append(ch);
	    		
	    		builder.append(String.valueOf(e));
	    	}
    	}
    	
    	return builder.toString();
    }

    public static <E> String joinInStr(E[] it, String ch)
    {
    	StringBuilder builder = new StringBuilder();
    	
    	if (it != null)
    	{
	    	for(E e: it)
	    	{
	    		if (builder.length() != 0)
	    			builder.append(ch);
	    		
	    		builder.append("\"");
	    		builder.append(String.valueOf(e));
	    		builder.append("\"");
	    	}
    	}
    	
    	return builder.toString();
    }

    public static <E> String joinInStr(Iterable<E> it, String ch)
    {
    	StringBuilder builder = new StringBuilder();
    	
    	if (it != null)
    	{
	    	for(E e: it)
	    	{
	    		if (builder.length() != 0)
	    			builder.append(ch);
	    		
	    		builder.append("\"");
	    		builder.append(String.valueOf(e));
	    		builder.append("\"");
	    	}
    	}
    	
    	return builder.toString();
    }
    
    public static <E> ArrayList<E> uniqueCollection(Collection<E> collection)
    {
    	HashSet<E> set = new HashSet<E>(collection);

    	ArrayList<E> list = new ArrayList<E>(set);

    	return list;
    }
      
	/**
	 * 隐藏字符串中间数值
	 * （用于手机号、银行卡号、姓名等敏感信息的处理）
	 * 
	 * @param str 待处理字符串
	 * @param left 左边显示位数  大于等于0
	 * @param right 右边显示位数  大于等于0
	 * @return 已处理字符串
	 */
	public static String hiddenStr(String str, Integer left, Integer right)
	{	
		if (StringUtils.isBlank(str))
			return str;
		
		if (left == null || left < 0)
			left = 0;
		
		if (right == null || right < 0)
			right = 0;

		if ((left + right) >= str.length())
			return str;

		return str.substring(0, left) +
			Utils.joinStr(Collections.nCopies(str.length() - (left + right), "*"), "") +
			str.substring(str.length() - right);
	}
	
    /**
     * 导出
     * 
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
    
    /**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String> importCsv(File file){
        List<String> dataList=new ArrayList<String>();
        
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new FileReader(file));
            String line = ""; 
            while ((line = br.readLine()) != null) { 
                dataList.add(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return dataList;
    }
	
    public static String formatException(Throwable e)
    {
    	StringWriter writer = new StringWriter();
    	
    	e.printStackTrace(new PrintWriter(writer));
    	
    	return writer.toString();
    }

	public static String mask(
		String text, int length, String mode)
	{
		char[] orgiseq = text.toCharArray();
		char[] charseq = new char[text.length()];

		if (length > orgiseq.length)
			length = orgiseq.length;

		Arrays.fill(charseq, '*');

		if (mode.indexOf("p") != -1)
			System.arraycopy(orgiseq, 0, charseq, 0, length);
		
		if (mode.indexOf("s") != -1)
			System.arraycopy(orgiseq,
				orgiseq.length - length,
				charseq, charseq.length - length, length);

		return new String(charseq);
	}
    
    public static <S, T> T copyProperties(
    	S source, T target, String[] propertyNames)
    {
    	Set<String> names =
    		((propertyNames != null)?
    			new HashSet<String>(Arrays.asList(propertyNames)): null);

    	PropertyDescriptor[] targetPds =
    		BeanUtils.getPropertyDescriptors(target.getClass());

    	for(PropertyDescriptor targetPd: targetPds)
    	{
    		Method writeMethod = targetPd.getWriteMethod();
    		if (writeMethod == null ||
    			(names != null && !names.contains(targetPd.getName())))
    			continue;
    		
    		PropertyDescriptor sourcePd =
    			BeanUtils.getPropertyDescriptor(
    				source.getClass(), targetPd.getName());
    		if (sourcePd == null)
    			continue;
    		
			Method readMethod = sourcePd.getReadMethod();
			if (readMethod != null &&
				ClassUtils.isAssignable(
					writeMethod.getParameterTypes()[0],
					readMethod.getReturnType()))
			{
				try
				{
					if (!Modifier.isPublic(
						readMethod.getDeclaringClass().getModifiers()))
					{
						readMethod.setAccessible(true);
					}
					
					Object value = readMethod.invoke(source);
					
					if (!Modifier.isPublic(
						writeMethod.getDeclaringClass().getModifiers()))
					{
						writeMethod.setAccessible(true);
					}

					writeMethod.invoke(target, value);
				} catch (Throwable ex)
				{
					ex.printStackTrace();
				}
			}
    	}

    	return target;
    }
}
