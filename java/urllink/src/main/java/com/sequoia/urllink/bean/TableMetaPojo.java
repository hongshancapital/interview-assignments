package com.sequoia.urllink.bean;

import com.sequoia.urllink.base.model.AbstractPojo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Setter
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TableMetaPojo extends AbstractPojo<Long> {
    private static final long serialVersionUID = 1L;
    private String dataType;
    private String name;
    private String tmpName;
    private Integer type;
}
