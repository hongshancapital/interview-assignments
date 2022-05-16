package com.redwood.urlshorter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class SavedShortUrl {
	private String key;
	private String url;
}
