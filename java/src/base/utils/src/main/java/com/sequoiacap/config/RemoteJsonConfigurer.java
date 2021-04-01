package com.sequoiacap.config;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import com.sequoiacap.exception.BusinessException;
import com.sequoiacap.utils.CryptoHelper;
import com.sequoiacap.utils.HttpHelper;
import com.sequoiacap.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.StringValueResolver;

import com.google.gson.JsonElement;

public class RemoteJsonConfigurer
	implements BeanFactoryPostProcessor,
		InitializingBean, BeanNameAware, BeanFactoryAware
{
	private static final Logger logger =
    	LoggerFactory.getLogger(RemoteJsonConfigurer.class);

	private String url;

	private String prefix = "@{";
	private String suffix = "}";

	private String jsonProperty = "config.json";

	private String configDigest;
	private JsonElement config;

	public String resolveConfig(String name)
	{
		String defValue = null, value = null;

		int defaultIndex = name.indexOf(":");
		if (defaultIndex != -1)
		{
			defValue = name.substring(defaultIndex + 1);
			name = name.substring(0, defaultIndex);
		}

		value = System.getProperty(name);
		if (value != null)
			return value;

		JsonElement jsonVal = JsonHelper.path(config, name);
		if (jsonVal != null)
		{
			if (jsonVal.isJsonPrimitive())
			{
				value = jsonVal.getAsString();
			} else
			{
				value = jsonVal.toString();
			}
		} else
		value = defValue;

		if (value == null)
		{
			String error =
				String.format(
					"failed to resolve configuration %s", name);

			logger.error(error);
			
			throw new BusinessException(10501, error);
		}

		logger.info(String.format("%s=%s", name, value));
		
		return value;
	}
	
	public String resolveExpr(String strVal)
	{
		int lastIndex = 0;

		StringBuilder result = new StringBuilder();

		do
		{
			int startIndex = 0, endIndex = 0;

			startIndex = strVal.indexOf(prefix, lastIndex);
			if (startIndex == -1)
				break;

			if (lastIndex < startIndex)
			{
				result.append(strVal.substring(lastIndex, startIndex));
			}

			startIndex += prefix.length();
			endIndex = strVal.indexOf(suffix, startIndex);
			if (endIndex == -1)
			{
				endIndex = strVal.length();
			}

			String name =
				strVal.substring(startIndex, endIndex);
			
			result.append(resolveConfig(name));

			lastIndex = endIndex + suffix.length();
		} while(lastIndex < strVal.length());

		if (lastIndex < strVal.length())
		{
			result.append(strVal.substring(lastIndex));
		}

		return result.toString();
	}


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
		throws BeansException
	{
		String[] names = beanFactory.getBeanDefinitionNames();

		StringValueResolver resolver = new StringValueResolver() {
			@Override
			public String resolveStringValue(String strVal)
			{
				return resolveExpr(strVal);
			}				
		};

		BeanDefinitionVisitor visitor =
			new BeanDefinitionVisitor(resolver);

		for(String name: names)
		{
			BeanDefinition bean = 
				beanFactory.getBeanDefinition(name);

			visitor.visitBeanDefinition(bean);
		}

		beanFactory.resolveAliases(resolver);

		beanFactory.addEmbeddedValueResolver(resolver);
	}

	public void refresh()
	{
		try
		{
			String content = "";

			if (StringUtils.isBlank(content) &&
				System.getProperty("config.json") != null)
			{
				content = System.getProperty(jsonProperty);

				logger.info("success to get configuration from system property config.json");
			}

			if (StringUtils.isBlank(content))
			{
				content =
					HttpHelper.sendGet(
						url, new HashMap<String, String>());

				logger.info(String.format(
					"success to get configuration from %s", url));
			}

			logger.info(content);

			String digest = CryptoHelper.digest(content, "utf-8", "sha-256");
			if (digest.equals(configDigest))
			{
				logger.info("same digest, ignore it.");
				return;
			}

			config = JsonHelper.parseJson(content);
			configDigest = digest;

			logger.info(String.valueOf(config));
		} catch(Exception e)
		{
			logger.error(
				String.format(
					"failed to get configuration from %s", url),
				e);
		}
	}

	@Override
	public void afterPropertiesSet()
		throws Exception
	{
		refresh();
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory)
		throws BeansException
	{ }

	@Override
	public void setBeanName(String name)
	{
		logger.info(name);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getConfigDigest() {
		return configDigest;
	}
}
