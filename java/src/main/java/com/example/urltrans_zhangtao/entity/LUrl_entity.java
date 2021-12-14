package com.example.urltrans_zhangtao.entity;

public class LUrl_entity {
    private String LUrl;
    private String LUrl_trans;

    public void setLUrl(String LUrl) {
        this.LUrl = LUrl;
    }

    public void setLUrl_trans(String LUrl_trans) {
        this.LUrl_trans = LUrl_trans;
    }

    public String getLUrl() {
        return LUrl;
    }

    public String getLUrl_trans() {
        return LUrl_trans;
    }

    @Override
    public String toString() {
        return "LUrl_entity{" +
                "LUrl='" + LUrl + '\'' +
                ", LUrl_trans='" + LUrl_trans + '\'' +
                '}';
    }
}
