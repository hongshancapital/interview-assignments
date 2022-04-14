package com.sequoiacap;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

import com.sequoiacap.annotation.Generated;

/**
 * 
 * @author zoubin
 *
 */
@Generated
public class HighPerformanceTomcatConnectorCustomizer implements TomcatConnectorCustomizer {

	@Override
	public void customize(Connector connector) {
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		protocol.setKeepAliveTimeout(30000);
		protocol.setMaxThreads(1000);
		protocol.setMaxKeepAliveRequests(10000);		
	}

}
