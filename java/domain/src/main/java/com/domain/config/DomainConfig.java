package com.domain.config;

import com.domain.bean.DomainConfigBean;
import com.domain.commons.Constants;
import com.domain.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author ：ji
 * @date ：2021/11/19
 * @description：系统配置
 */
@Configuration
public class DomainConfig {

    /**
     * 配置的机器编号
     */
    @Value("${domain.machineId}")
    private Integer machineId;

    /**
     * 短域名服务地址 举例：https://d.cn/Qed3T567
     */
    @Value("${domain.server.url}")
    private String serverUrl;

    /**
     * 系统配置Bean
     * 1、机器编号，以从启动配置中为主，如果未配置，则从配置文件中获取
     * @return
     */
    @Bean
    public DomainConfigBean domainConfigBean(){
        DomainConfigBean bean = new DomainConfigBean();
        bean.setServerUrl(serverUrl);
        bean.setMachineId(machineId);
        String domainMachineId = System.getProperty(Constants.DM);
        if (StringUtils.hasText(domainMachineId) && CommonUtil.isNumber(domainMachineId)){
            bean.setMachineId(Integer.valueOf(domainMachineId));
        }
        return bean;
    }
}
