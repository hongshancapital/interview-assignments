package com.sequoiacap;

import springfox.documentation.service.Contact;

/**
 *@ClassName: CommonSwagger2Configuration
 *@Description: 公共swagger配置接口
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
public interface CommonSwagger2Configuration {

  String termsOfServiceUrl = "http://docs.sequoiacap.com/docs-sequoiacap-docs-api/";
  Contact contact = new Contact("sequoiacap", "", "");
  String version = "1.0";
}
