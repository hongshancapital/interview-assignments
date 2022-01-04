package me.huchao.sd.domain.url.repos;

import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.model.Node;

/**
 * @author huchao
 */
public interface SlotRepos {

    long prefetchOffset(String slotName, int preFetchOffsetSize);

    void insertNode(Node node) throws DomainException;

    Node getNodeByOrigin(String slotName, String origin);

    Node getNodeByCode(String slotName, String code);
}
