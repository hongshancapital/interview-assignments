package com.sequoiacap.json;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class JsonObjectMapper
	implements FactoryBean<ObjectMapper>, InitializingBean
{
	@Override
	public void afterPropertiesSet()
		throws Exception
	{ }

	@Override
	public ObjectMapper getObject()
		throws Exception
	{
		//ibernate5Module module = new Hibernate5Module();
		
		//module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		
		//return Jackson2ObjectMapperBuilder.json()
		//	.modules(module).build();
		
		return Jackson2ObjectMapperBuilder.json().build();
	}

	@Override
	public Class<?> getObjectType()
	{
		return JsonObjectMapper.class;
	}
}
