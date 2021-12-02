package org.hkm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrl {

    private String key;
    private String originUrl;
    private Long expireTimeStamp;

}
