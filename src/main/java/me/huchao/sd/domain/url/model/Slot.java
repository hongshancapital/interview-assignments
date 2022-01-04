package me.huchao.sd.domain.url.model;

import lombok.Getter;
import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.repos.SlotRepos;

/**
 * @author huchao
 */
@Getter
public class Slot {

    private static final String MAPPING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-";

    private int codeLength;

    private String slotName;

    private int preFetchOffsetSize;

    private SlotRepos slotRepos;

    private long maxAvailableOffset;

    private long currentOffset;

    public Slot(String slotName, int codeLength, SlotRepos slotRepos, int preFetchOffsetSize) {
        super();
        if (null == slotName || slotName.isEmpty()) {
            throw new IllegalArgumentException("slot name of slot can not be empty");
        }
        if (codeLength < 1) {
            throw new IllegalArgumentException("code length of slot can not be less than 1");
        }
        if (null == slotRepos) {
            throw new IllegalArgumentException("slot repos of slot can not be null");
        }
        if (preFetchOffsetSize < 1) {
            throw new IllegalArgumentException("preFetchOffsetSize of slot can not be less than 1");
        }
        this.slotName = slotName;
        this.codeLength = codeLength;
        this.preFetchOffsetSize = preFetchOffsetSize;
        this.slotRepos = slotRepos;
        this.preFetchOffset();
    }

    private void preFetchOffset() {
        this.maxAvailableOffset = this.slotRepos.prefetchOffset(this.slotName, this.preFetchOffsetSize);
        this.currentOffset = this.maxAvailableOffset - preFetchOffsetSize;
    }

    private synchronized long nextOffset() {
        long nextOffset = this.currentOffset++;
        if (this.currentOffset == this.maxAvailableOffset + preFetchOffsetSize) {
            this.preFetchOffset();
        }
        return nextOffset;
    }

    private String mapping(long offset) {
        int divider = MAPPING.length();
        long dividend = offset;
        StringBuilder sb = new StringBuilder();
        while (dividend > 0) {
            sb.append(MAPPING.charAt((int) (dividend % divider)));
            dividend = dividend / divider;
        }
        while (sb.length() < codeLength) {
            sb.append(MAPPING.charAt(0));
        }
        String shortUrl = slotName + sb.reverse().toString();
        return shortUrl;
    }

    public Node nextNode(String origin) throws DomainException {
        if (null == origin || origin.isEmpty()) {
            throw new IllegalArgumentException("origin can not be empty");
        }
        long offset = nextOffset();
        String shortUrl = mapping(offset);
        Node node = new Node(this.slotName, offset, origin, shortUrl);
        try {
            this.slotRepos.insertNode(node);
        } catch (Exception e) {
            throw new DomainException(e);
        }
        return node;
    }

    public Node getNodeByOrigin(String origin) throws DomainException {
        if (null == origin || origin.isEmpty()) {
            throw new IllegalArgumentException("origin can not be empty");
        }
        try {
            Node node = this.slotRepos.getNodeByOrigin(this.slotName, origin);
            if (node == null) {
                node = nextNode(origin);
            }
            return node;
        } catch (Exception e) {
            throw new DomainException(e);
        }
    }

    public Node getNodeByCode(String code) {
        if (null == code || code.isEmpty()) {
            throw new IllegalArgumentException("code can not be empty");
        }
        return this.slotRepos.getNodeByCode(this.slotName, code);
    }

}
