package me.huchao.sd.repos.memory;

import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huchao
 */
public class MemorySlotRepos {

    private ConcurrentHashMap<String, SlotData> map;

    private long slotSize;

    public MemorySlotRepos(long slotSize) {
        super();
        if (slotSize < 1) {
            throw new IllegalArgumentException("slot size can not be less than 1");
        }
        this.map = new ConcurrentHashMap<>();
        this.slotSize = slotSize;
    }

    private SlotData getSlotData(String slotName) {
        SlotData slotData = map.get(slotName);
        if(null != slotData) {
            return slotData;
        }
        slotData = new SlotData(this.slotSize);
        SlotData preValue = map.putIfAbsent(slotName, slotData);
        if (preValue == null) {
            return slotData;
        }
        return preValue;
    }

    public long prefetchOffset(String slotName, int preFetchOffsetSize) {
        SlotData slotData = this.getSlotData(slotName);
        return slotData.prefetchOffset(preFetchOffsetSize);
    }

    public void insertNode(Node node) throws DomainException {
        SlotData slotData = this.getSlotData(node.getSlotName());
        slotData.insertNode(node);
    }

    public Node getNodeByOrigin(String slotName, String origin) {
        SlotData slotData = this.getSlotData(slotName);
        return slotData.getNodeByOrigin(origin);
    }

    public Node getNodeByCode(String slotName, String code) {
        SlotData slotData = this.getSlotData(slotName);
        return slotData.getNodeByCode(code);
    }

    public static class SlotData {
        Map<String, Node> originMap;
        Map<String, Node> codeMap;
        long currentOffset;
        private long size;
        private long usedOffset;

        public SlotData(long size) {
            super();
            this.originMap = new HashMap<>();
            this.codeMap = new HashMap<>();
            this.currentOffset = 0;
            this.usedOffset = 0;
            this.size = size;
        }

        public synchronized long prefetchOffset(int preFetchOffsetSize) {
            currentOffset = currentOffset + preFetchOffsetSize;
            return currentOffset;
        }

        public void insertNode(Node node) throws DomainException {
            if (usedOffset >= size) {
                throw new DomainException("slot excced with size: " + size);
            }
            codeMap.put(node.getCode(), node);
            originMap.put(node.getOrigin(), node);
            this.usedOffset++;
        }

        public Node getNodeByOrigin(String origin) {
            return originMap.get(origin);
        }

        public Node getNodeByCode(String code) {
            return codeMap.get(code);
        }

    }
}
