package ks.sequoia.eobj;

import lombok.Data;

import java.sql.Timestamp;

@Data
public abstract class AbstractEObj implements EObj {
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 修改时间
     */
    private Timestamp updateTime;
    /**
     * 创建人
     */
    private long createId;
    /**
     * 更新人Id
     */
    private long updateId;
}
