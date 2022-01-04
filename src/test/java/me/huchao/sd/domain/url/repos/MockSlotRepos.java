package me.huchao.sd.domain.url.repos;

import me.huchao.sd.domain.url.model.Node;

import java.util.HashMap;
import java.util.Map;

public class MockSlotRepos implements SlotRepos {

    Map<String, Node> originMap;
    Map<String, Node> codeMap;
    Map<String, Integer> startOffsetMap;

    public MockSlotRepos() {
        super();
        originMap = new HashMap<>();
        codeMap = new HashMap<>();
        startOffsetMap = new HashMap<>();
    }

    @Override
    public long prefetchOffset(String slotName, int preFetchOffsetSize) {
        Integer startOffset = this.startOffsetMap.get(slotName);
        if (startOffset == null) {
            startOffset = 1000;
        }
        startOffset = startOffset + preFetchOffsetSize;
        this.startOffsetMap.put(slotName, startOffset);
        return startOffset;
    }

    @Override
    public void insertNode(Node node) {
        codeMap.put(node.getCode(), node);
        originMap.put(node.getOrigin(), node);
        return;
    }

    @Override
    public Node getNodeByOrigin(String slotName, String origin) {
        return originMap.get(origin);
    }

    @Override
    public Node getNodeByCode(String slotName, String code) {
        return codeMap.get(code);
    }
}
