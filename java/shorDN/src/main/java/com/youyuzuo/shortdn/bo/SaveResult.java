package com.youyuzuo.shortdn.bo;

import java.io.Serializable;

public class SaveResult implements Serializable {

    private String shortDN;
    private String longDN;

    public SaveResult(String shortDN, String longDN) {
        this.shortDN = shortDN;
        this.longDN = longDN;
    }

    public String getShortDN() {
        return shortDN;
    }

    public void setShortDN(String shortDN) {
        this.shortDN = shortDN;
    }

    public String getLongDN() {
        return longDN;
    }

    public void setLongDN(String longDN) {
        this.longDN = longDN;
    }
}
