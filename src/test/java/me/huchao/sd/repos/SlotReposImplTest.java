package me.huchao.sd.repos;

import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.domain.url.repos.MockSlotRepos;
import me.huchao.sd.repos.memory.MemorySlotRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
* <p> SlotReposImpl Tester. </p>
* <p> 2021-12-30 12:29:48.553 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class SlotReposImplTest {

    private MemorySlotRepos memorySlotRepos;;

    private String slotName;

    @BeforeEach
    public void setup() {
        this.memorySlotRepos = new MemorySlotRepos(10000);
        this.slotName = "test";
    }

    /**
    *
    * Method: SlotReposImpl(memorySlotRepos)
    */
    @Test
    public void SlotReposImplTest() throws Exception {
        SlotReposImpl slotReposImpl = new SlotReposImpl(this.memorySlotRepos);
    }

    /**
    *
    * Method: prefetchOffset(slotName, preFetchOffsetSize)
    */
    @Test
    public void prefetchOffsetTest() throws Exception {
        SlotReposImpl slotReposImpl = new SlotReposImpl(this.memorySlotRepos);
        long offset = slotReposImpl.prefetchOffset(this.slotName, 100);
        assertEquals(100, offset);
        offset = slotReposImpl.prefetchOffset(this.slotName, 100);
        assertEquals(200, offset); }

    /**
    *
    * Method: insertNode(node)
    */
    @Test
    public void insertNodeTest() throws Exception {
        SlotReposImpl slotReposImpl = new SlotReposImpl(this.memorySlotRepos);
        Node node = new Node(this.slotName, 1, "testOrigin", "testCode");
        slotReposImpl.insertNode(node);
    }

    /**
    *
    * Method: getNodeByOrigin(slotName, origin)
    */
    @Test
    public void getNodeByOriginTest() throws Exception {
        SlotReposImpl slotReposImpl = new SlotReposImpl(this.memorySlotRepos);
        Node node = new Node(this.slotName, 1, "testOrigin", "testCode");
        slotReposImpl.insertNode(node);
        Node node1 = slotReposImpl.getNodeByOrigin(this.slotName, "testOrigin");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }

    /**
    *
    * Method: getNodeByCode(slotName, code)
    */
    @Test
    public void getNodeByCodeTest() throws Exception {
        SlotReposImpl slotReposImpl = new SlotReposImpl(this.memorySlotRepos);
        Node node = new Node(this.slotName, 1, "testOrigin", "testCode");
        slotReposImpl.insertNode(node);
        Node node1 = slotReposImpl.getNodeByCode(this.slotName, "testCode");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }


}
