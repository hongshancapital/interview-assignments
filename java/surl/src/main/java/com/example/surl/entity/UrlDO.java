package com.example.surl.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author 杨欢
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UrlDO {

    public UrlDO(String surl, String lurl) {
        this.surl = surl;
        this.lurl = lurl;
    }


    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty(notes = "短链接")
    private String surl;

    @ApiModelProperty(notes = "长链接")
    private String lurl;

    private Date createdAt;
}
