package org.pp.dubbo.hession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.dubbo.common.serialize.hessian2.Hessian2SerializerFactory;
import org.apache.log4j.Logger;
import org.hibernate.collection.internal.AbstractPersistentCollection;
//import org.hibernate.collection.internal.AbstractPersistentCollection;
import org.pp.dubbo.serializer.GsonDeserializer;
import org.pp.dubbo.serializer.GsonSerializer;
import org.pp.dubbo.serializer.PageDeserializer;
import org.pp.dubbo.serializer.PageSerializer;
import com.sequoiacap.utils.AnnotationHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializerFactory;
import com.alibaba.com.caucho.hessian.io.Deserializer;
import com.alibaba.com.caucho.hessian.io.HessianProtocolException;
import com.alibaba.com.caucho.hessian.io.Serializer;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;


public class SerializerRegister
{
	private static final Logger log = Logger.getLogger(SerializerRegister.class);

	public SerializerRegister()
	{
		register(Point.class);
		register(PageRequest.class);
		register(Pageable.class, PageRequest.class);

		register(Page.class, new PageSerializer(), new PageDeserializer());
		register(PageImpl.class, new PageSerializer(), new PageDeserializer());

		registerPersistCollection();
	}

	public static void registerPersistCollection()
	{
		Hessian2SerializerFactory.SERIALIZER_FACTORY.addFactory(new AbstractSerializerFactory() {
			@Override
			public Serializer getSerializer(Class cl)
				throws HessianProtocolException
			{
				if (AbstractPersistentCollection.class.isAssignableFrom(cl))
				{
					return new Serializer() {
						@Override
						public void writeObject(Object obj, AbstractHessianOutput out)
							throws IOException
						{
							AbstractPersistentCollection persistenCollection =
								(AbstractPersistentCollection) obj;

							if (!persistenCollection.wasInitialized())
							{
								if (obj instanceof List)
								{
									out.writeObject(new ArrayList());
								} else
								if (obj instanceof Set)
								{
									out.writeObject(new HashSet());
								} else
								if (obj instanceof Map)
								{
									out.writeObject(new HashMap());
								}

								return;
							}

							if (obj instanceof List)
							{
								out.writeObject(new ArrayList((List) obj));
							} else
							if (obj instanceof Set)
							{
								out.writeObject(new HashSet((Set) obj));
							} else
							if (obj instanceof Map)
							{
								out.writeObject(new HashMap((Map) obj));
							}
						}
					};
				}

				return null;
			}

			@Override
			public Deserializer getDeserializer(Class cl)
				throws HessianProtocolException
			{
				return null;
			}
		});
	}
	
	public static void register(Class<?> clazz)
	{
		register(clazz, new GsonSerializer(), new GsonDeserializer(clazz));
	}

	public static void register(Class<?> inter, Class<?> impl)
	{
		register(inter, new GsonSerializer(), new GsonDeserializer(impl));
	}
	
	public static void register(
		final Class<?> clazz,
		final Serializer serializer, final Deserializer deserializer)
	{
		Hessian2SerializerFactory.SERIALIZER_FACTORY.addFactory(new AbstractSerializerFactory() {
			@Override
			public Serializer getSerializer(Class cl)
				throws HessianProtocolException
			{
				if (clazz.equals(cl))
					return serializer;

				return null;
			}

			@Override
			public Deserializer getDeserializer(Class cl)
				throws HessianProtocolException
			{
				if (clazz.equals(cl))
					return deserializer;

				return null;
			}
		});
	}
	
	public static void register2(Class<?> clazz,
		Serializer serializer, Deserializer deserializer)
	{
		try
		{
			HashMap _staticSerializerMap =
				AnnotationHelper.getStaticField(
					SerializerFactory.class, "_staticSerializerMap");
			HashMap _staticDeserializerMap =
				AnnotationHelper.getStaticField(
					SerializerFactory.class, "_staticDeserializerMap");
			
			_staticSerializerMap.put(clazz, serializer);
			_staticDeserializerMap.put(clazz, deserializer);
		} catch(Exception e)
		{
			log.error("", e);
		}
	}
}
