package scdt.interview.java.common.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenRequest {
	// 长域名
	private String longUrl;
	// 参数1，渠道等可能
	private String from;
}
