package com.ttts.urlshortener.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class LUrlReq implements Serializable {
    //长链
    String lurl;
}
