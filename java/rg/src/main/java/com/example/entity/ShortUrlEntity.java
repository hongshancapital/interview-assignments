package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("短域名实体")
public class ShortUrlEntity {

	
	@ApiModelProperty("ID")
    private Integer Id;
	
	@ApiModelProperty("短域名key")
    private String shortUrl;

    @ApiModelProperty("长域名val")
    private String longUrl;

    
    
    
    @Override
	public String toString() {
		return "ShortUrlEntity [Id=" + Id + ", shortUrl=" + shortUrl + ", longUrl=" + longUrl + "]";
	}

	public ShortUrlEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ShortUrlEntity(Integer Id,String shortUrl, String longUrl)
    {
        this.Id = Id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	
    
    
}
