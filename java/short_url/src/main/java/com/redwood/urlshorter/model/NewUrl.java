package com.redwood.urlshorter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class NewUrl {
	@NotBlank(message = "Field [url] should not be blank")
	@Size(min = 9, max = 2048, message = "Field [url] length should be more than 8 and less or equal to 2048")
	private String url;
}
