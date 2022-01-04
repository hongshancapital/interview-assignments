package me.huchao.sd.domain.url.model;

import lombok.Getter;

/**
 * @author huchao
 */
@Getter
public class Node {

    private String slotName;

    private long offset;

    private String origin;

    private String code;

    public Node(String slotName, long offset, String origin, String code) {
        super();
        if (null == slotName || slotName.isEmpty()) {
            throw new IllegalArgumentException("slot name of node can not be empty");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset of node can less than zero");
        }
        if (null == origin) {
            throw new IllegalArgumentException("origin of node can not be null");
        }
        if (null == code) {
            throw new IllegalArgumentException("code of node can not be null");
        }
        this.slotName = slotName;
        this.offset = offset;
        this.origin = origin;
        this.code = code;
    }

}
