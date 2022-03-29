package com.wg.vo;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class DomainVO implements Serializable {

    @NotEmpty
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
