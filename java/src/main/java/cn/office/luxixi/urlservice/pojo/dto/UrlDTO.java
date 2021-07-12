package cn.office.luxixi.urlservice.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UrlDTO implements Serializable {

    private String longUrl;
    private String shortUrl;
    private int longHash;
    private int shortHash;
}
