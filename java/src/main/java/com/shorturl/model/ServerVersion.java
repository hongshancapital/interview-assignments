package com.shorturl.model;

import java.io.Serializable;

/**
 * Created by ruohanpan on 21/3/23.
 */
public class ServerVersion implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
