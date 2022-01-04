package me.huchao.sd.repos.memory;

import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.domain.url.model.Slot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* <p> SlotData Tester. </p>
* <p> 2021-12-30 12:29:48.524 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class SlotDataTest {

    /**
    *
    * Method: SlotData()
    */
    @Test
    public void SlotDataTest() throws Exception {
        MemorySlotRepos.SlotData slotData = new MemorySlotRepos.SlotData(10000);
    }

    /**
    *
    * Method: prefetchOffset(preFetchOffsetSize)
    */
    @Test
    public void prefetchOffsetTest() throws Exception {
        MemorySlotRepos.SlotData slotData = new MemorySlotRepos.SlotData(10000);
        long offset = slotData.prefetchOffset(100);
        assertEquals(100, offset);
        offset = slotData.prefetchOffset(100);
        assertEquals(200, offset);
    }

    /**
    *
    * Method: insertNode(node)
    */
    @Test
    public void insertNodeTest() throws Exception {
        MemorySlotRepos.SlotData slotData = new MemorySlotRepos.SlotData(10000);
        Node node = new Node("test", 1, "testOrigin", "testCode");
        slotData.insertNode(node);
        final MemorySlotRepos.SlotData slotData1 = new MemorySlotRepos.SlotData(1);
        node = new Node("test1", 1, "testOrigin1", "testCode2");
        slotData1.insertNode(node);
        final Node node1 = new Node("test1", 2, "testOrigin1", "testCode2");
        assertThrows(
                DomainException.class,
                () -> slotData1.insertNode(node1));

    }

    /**
    *
    * Method: getNodeByOrigin(origin)
    */
    @Test
    public void getNodeByOriginTest() throws Exception {
        MemorySlotRepos.SlotData slotData = new MemorySlotRepos.SlotData(10000);
        Node node = new Node("test", 1, "testOrigin", "testCode");
        slotData.insertNode(node);
        Node node1 = slotData.getNodeByOrigin("testOrigin");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }

    /**
    *
    * Method: getNodeByCode(code)
    */
    @Test
    public void getNodeByCodeTest() throws Exception {
        MemorySlotRepos.SlotData slotData = new MemorySlotRepos.SlotData(10000);
        Node node = new Node("test", 1, "testOrigin", "testCode");
        slotData.insertNode(node);
        Node node1 = slotData.getNodeByCode("testCode");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }


}
