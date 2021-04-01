package com.sequoiacap.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbHelper
{
	public static <T> String formatXml(T object)
	{
		return formatXml(object, false);
	}

	public static <T> String formatXml(T object, Boolean xmlDec)
	{
		String result = null;
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(object.getClass());

			StringWriter writer = new StringWriter();
			
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, xmlDec);
			
			marshaller.marshal(object, writer);

			result = writer.toString();
		} catch(Exception e)
		{ }

		return result;
	}

	public static <T>
	    T convert(Object source, Class<T> target)
	{
		return convert(formatXml(source), target);
	}

	public static <T>
		T convert(String xml, Class<T> target)
	{
		T result = null;
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(target);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringReader reader = new StringReader(xml);

			result =(T) unmarshaller.unmarshal(reader);
		} catch(Exception e)
		{ }
		
		return result;
	}
}
