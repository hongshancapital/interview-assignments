package com.icbc.gjljfl.entity;

public class NxHealthData {

    private String startTime;
    private String serviceName;

    private String positon;

    private String matternName;

    private String result;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPositon() {
        return positon;
    }

    public void setPositon(String positon) {
        this.positon = positon;
    }

    public String getMatternName() {
        return matternName;
    }

    public void setMatternName(String matternName) {
        this.matternName = matternName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return startTime + "-" + serviceName + "-" + positon + "-" + matternName + "-" + result;
    }
}
