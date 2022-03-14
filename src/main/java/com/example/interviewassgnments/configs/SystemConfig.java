/**
 * this is a test project
 */

package com.example.interviewassgnments.configs;

import com.example.interviewassgnments.entitys.DacService;
import com.example.interviewassgnments.entitys.DomainNameOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Auther: maple
 * @Date: 2022/1/19 10:02
 * @Description: com.example.interviewassgnments.configs
 * @version: 1.0
 */
@Configuration
public class SystemConfig {

    @Bean(name="domainOptions")
    public DomainNameOptions domainNameOptions(){
        DomainNameOptions domainInfo = new DomainNameOptions();
        return domainInfo;
    }

    @Bean(name="dacService")
    public DacService dacService(){
        DacService dacService = new DacService(1,100000);
        return dacService;
    }
}
