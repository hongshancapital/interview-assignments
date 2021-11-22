package com.domain.bean;

/**
 * @author ：ji
 * @date ：2021/11/19
 * @description：默认系统配置Bean
 */
public class DomainConfigBean {

    /**
     * 配置的机器编号
     */
    private Integer machineId;
    /**
     * 服务地址
     */
    private String serverUrl;

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

}
