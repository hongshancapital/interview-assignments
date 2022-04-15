package com.sequoia.urllink.bean;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Setter
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GenCodeParam {
    /**
     * 长链
     */
    private String longUrl;
    /**
     * 附加字串
     */
    private List<String> attachList;
}
