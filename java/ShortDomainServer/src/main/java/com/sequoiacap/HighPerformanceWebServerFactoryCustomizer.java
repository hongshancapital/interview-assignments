package com.sequoiacap;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

import com.sequoiacap.annotation.Generated;

@Generated
public class HighPerformanceWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.setPort(Integer.parseInt(System.getProperty("serverPort", "8080")));
		((TomcatServletWebServerFactory) factory).addConnectorCustomizers(new HighPerformanceTomcatConnectorCustomizer());		
	}

}
