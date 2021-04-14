package io.github.cubesky.scdtjava.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "io-github-cubesky-scdtjava-entity-LongUrlBean")
public class LongUrlBean {
    @ApiModelProperty(value = "长 URL")
    private String longUrl;

    public LongUrlBean() {}

    public LongUrlBean(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
