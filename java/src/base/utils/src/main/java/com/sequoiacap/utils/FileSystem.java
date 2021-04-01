package com.sequoiacap.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件系统相关辅助类
 */
public class FileSystem
{
	/**
	 * 将路径解析成字符串列表
	 * 
	 * @param pathname　路径字符串
	 * @return　字符串列表
	 */
	public static ArrayList<String> parsePath(String pathname)
	{
		ArrayList<String> path = new ArrayList<String>();

		String[] result = pathname.split("\\/");
		for(int index = 0; index != result.length; ++index)
		{
			path.add(result[index]);
		}

		return path;
	}

	/**
	 * 将字符串列表组成路径
	 * 
	 * @param path 字符串列表
	 * @return　路径字符串
	 */
	public static String makePath(List<String> path)
	{
		StringBuilder builder = new StringBuilder();

		for(int index = 0; index != path.size(); ++index)
		{
			if (index != 0)
				builder.append('/');

			builder.append(path.get(index));
		}

		return builder.toString();
	}

	/**
	 * 复制文件
	 * 
	 * @param src　源文件
	 * @param dst　目标文件
	 * @return　字节数量
	 * 
	 * @throws IOException
	 */
	public static int copyFile(File src, File dst)
		throws IOException
	{
		int bytecount = 0;

		if (dst.exists())
			dst.delete();

		dst.createNewFile();

		FileInputStream input = new FileInputStream(src);
		FileOutputStream output = new FileOutputStream(dst);

		byte[] buffer = new byte[256];

		while(input.available() > 0)
		{
			int count = input.read(buffer);

			output.write(buffer, 0, count);

			bytecount += count;
		}

		output.close();
		input.close();

		return bytecount;
	}

	/**
	 * 复制目录
	 * 
	 * @param src　源目录
	 * @param dst　目标目录
	 * @return　文件数量
	 * @throws IOException
	 */
	public static int copyAll(File src, File dst)
		throws IOException
	{
		int filecount = 0;

		if (src.isFile())
		{
			copyFile(src, dst);
			return 1;
		}

		if (src.isDirectory())
		{
			dst.mkdirs();

			for(File child: src.listFiles())
			{
				File target = new File(dst, child.getCanonicalPath());

				filecount += copyAll(child, target);
			}
		}

		return filecount;
	}

	/**
	 * 从流中读取所有字节
	 * 
	 * @param input　流
	 * @return　字节数组
	 */
	public static byte[] readBytes(InputStream input)
	{
		byte[] bytes = new byte[256];
        int count = 0;

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try
        {
	        while(true)
	        {
	            count = input.read(bytes);
	            if (count == -1)
	                break;
	
	            output.write(bytes, 0, count);
	        }
	
	        output.close();
        } catch(Exception e)
        {
        	e.printStackTrace();
        }

        return output.toByteArray();
	}

	/**
	 * 从文件中读取所有字节
	 * 
	 * @param file　文件
	 * @return　字节数组
	 */
	public static byte[] readBytes(File file)
	{
		byte[] data = null;

		if (file.exists())
		{
			try
			{
				FileInputStream input = new FileInputStream(file);
				
				data = readBytes(input);
				
				input.close();
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return data;
	}

	/**
	 * 从流中读取字符串
	 * 
	 * @param input　流
	 * @param encoding　字符编码
	 * @return　字符串
	 */
	public static String readString(InputStream input, String encoding)
	{
		String content = null;
		
		byte[] data = readBytes(input);

		
		if (data != null)
		{
			try
			{
				content = new String(data, encoding);
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return content;
	}

	/**
	 * 从文件读取字符串
	 * 
	 * @param file 文件
	 * @param encoding　字符编码
	 * @return 字符串
	 */
	public static String readString(File file, String encoding)
	{
		String content = null;
		
		byte[] data = readBytes(file);

		
		if (data != null)
		{
			try
			{
				content = new String(data, encoding);
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return content;
	}

	/**
	 * 写文件
	 * 
	 * @param file　文件
	 * @param input　输入流
	 * @return 是否成功
	 */
	public static boolean writeData(File file, InputStream input)
	{
		try
		{
			if (file.exists())
				file.delete();
			
			file.createNewFile();

			FileOutputStream output = new FileOutputStream(file);
			
			if (input != null)
				writeData(output, input);
			
			output.close();
			
			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 向输出流中写数据
	 * 
	 * @param output 输出流
	 * @param input　输入流
	 * @return　是否成功
	 */
	public static boolean writeData(OutputStream output, InputStream input)
	{
		byte[] bytes = new byte[256];
		int count = 0;
		
		try
		{
			while(true)
			{
				count = input.read(bytes);
				if (count == -1)
					break;
				
				output.write(bytes, 0, count);
			}
			
			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * 写文件
	 * 
	 * @param file 文件
	 * @param data　字符串数组
	 * @return　是否成功
	 */
	public static boolean writeBytes(File file, byte[] data)
	{
		try
		{
			if (file.exists())
				file.delete();
			
			file.createNewFile();
			
			
			FileOutputStream output = new FileOutputStream(file);
			
			if (data != null)
				output.write(data);
			
			output.close();
			
			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * 向输出流写数据
	 * 
	 * @param output 输出流
	 * @param data　数据
	 * @return　是否成功
	 */
	public static boolean writeBytes(OutputStream output, byte[] data)
	{
		try
		{			
			if (data != null)
				output.write(data);
			
			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * 写文件
	 * 
	 * @param file 文件
	 * @param content 字符串
	 * @param encoding 编码
	 * @return　是否成功
	 */
	public static boolean writeString(
		File file, String content, String encoding)
	{
		try
		{
			writeBytes(file, content.getBytes(encoding));

			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 写输出流
	 * 
	 * @param output 输出流
	 * @param content 字符串
	 * @param encoding 编码
	 * @return　是否成功
	 */
	public static boolean writeString(
		OutputStream output, String content, String encoding)
	{
		try
		{
			writeBytes(output, content.getBytes(encoding));

			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
