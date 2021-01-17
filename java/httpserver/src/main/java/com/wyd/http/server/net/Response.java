package com.wyd.http.server.net;

public final class Response {


    private String getStatus;
    private String context;
    private int contentLength;
    private String statusFlag;


    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
        this.statusFlag = "";
        if (this.getStatus.equals("200")) {
            this.statusFlag = "ok";
        }
        if (this.getStatus.equals("404")) {
            this.statusFlag = "Not Found";
        }

    }

    public String getContext() {
        return context;
    }


    public void setContext(byte[] bytes) {
        if (bytes == null) {
            this.context = "";
        }else {
            this.context = new String(bytes);
            this.contentLength=bytes.length;
        }

    }
}
