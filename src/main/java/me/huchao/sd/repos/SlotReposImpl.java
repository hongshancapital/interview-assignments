package me.huchao.sd.repos;

import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.domain.url.repos.SlotRepos;
import me.huchao.sd.repos.memory.MemorySlotRepos;

/**
 * @author huchao
 */
public class SlotReposImpl implements SlotRepos {

    private MemorySlotRepos memorySlotRepos;

    public SlotReposImpl(MemorySlotRepos memorySlotRepos) {
        super();
        this.memorySlotRepos = memorySlotRepos;
    }

    @Override
    public long prefetchOffset(String slotName, int preFetchOffsetSize) {
        return this.memorySlotRepos.prefetchOffset(slotName, preFetchOffsetSize);
    }

    @Override
    public void insertNode(Node node) throws DomainException {
        this.memorySlotRepos.insertNode(node);
    }

    @Override
    public Node getNodeByOrigin(String slotName, String origin) {
        return this.memorySlotRepos.getNodeByOrigin(slotName, origin);
    }

    @Override
    public Node getNodeByCode(String slotName, String code) {
        return this.memorySlotRepos.getNodeByCode(slotName, code);
    }
}
