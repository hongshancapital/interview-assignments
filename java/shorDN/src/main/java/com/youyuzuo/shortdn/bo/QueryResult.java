package com.youyuzuo.shortdn.bo;

import java.io.Serializable;

public class QueryResult implements Serializable {

    private String shortDN;
    private String longDN;

    public QueryResult(String shortDN, String longDN) {
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
