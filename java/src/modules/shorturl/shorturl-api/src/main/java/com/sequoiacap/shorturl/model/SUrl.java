
package com.sequoiacap.shorturl.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(
    indexes = {
        @Index(columnList = "shortUrl", unique = false),
        @Index(columnList = "shortUrl, status", unique = false),
        @Index(columnList = "timestamp", unique = false),
        @Index(columnList = "type, status", unique = false),
        @Index(columnList = "ip", unique = false),
    }
)
public class SUrl
    implements Serializable
{
	public static enum Status {
		normal,
		invalid
	}
	
	public static enum Type {
		temporary,
		short_period,
		permanent
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8)
    private String shortUrl;

    @Column(length = 65535)
    private String url;

    private Timestamp timestamp;

    @Column(length = 42)
    private String ip;
    
    @Enumerated
    private Status status;

    @Enumerated
    private Type type;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}

