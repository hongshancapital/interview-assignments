package me.huchao.sd.domain.url.model;

import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.repos.MockSlotRepos;
import me.huchao.sd.domain.url.repos.SlotRepos;
import me.huchao.sd.repos.SlotReposImpl;
import me.huchao.sd.repos.memory.MemorySlotRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* <p> Slot Tester. </p>
* <p> 2021-12-29 12:30:47.924 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class SlotTest {

    private SlotRepos slotRepos;

    @BeforeEach
    public void setup() {
        this.slotRepos = new MockSlotRepos();
    }

    /**
    *
    * Method: Slot(slotName, codeLength, mapping, slotRepos, preFetchOffsetSize)
    */
    @Test
    public void SlotTest() throws Exception {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Slot(null, 6, this.slotRepos, 100));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Slot("ab", 0, this.slotRepos, 100));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Slot("ab", 6, null, 100));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Slot("ab", 6, this.slotRepos, -1));
        Slot slot = new Slot("ab", 6, this.slotRepos, 100);
        assertEquals("ab", slot.getSlotName());
        assertEquals(6, slot.getCodeLength());
        assertEquals(this.slotRepos, slot.getSlotRepos());
        assertEquals(100, slot.getPreFetchOffsetSize());
    }

    /**
    *
    * Method: nextNode(origin)
    */
    @Test
    public void nextNodeTest() throws Exception {
        Slot slot = new Slot("ab", 6, this.slotRepos, 1);
        Node node = slot.nextNode("www.baidu.com");
        assertEquals("ab", node.getSlotName());
        assertEquals("ab0000fE", node.getCode());
        assertEquals("www.baidu.com", node.getOrigin());
        assertEquals(1000, node.getOffset());
        node = slot.nextNode("www.taobao.com");
        assertEquals("ab", node.getSlotName());
        assertEquals("ab0000fF", node.getCode());
        assertEquals("www.taobao.com", node.getOrigin());
        assertEquals(1001, node.getOffset());
        assertThrows(
                IllegalArgumentException.class,
                () -> slot.nextNode(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> slot.nextNode(""));

        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(1);
        SlotRepos slotRepos1 = new SlotReposImpl(memorySlotRepos);
        final Slot slot1 = new Slot("abc", 6, slotRepos1, 1);
        slot1.nextNode("www.baidu.com");
        assertThrows(
                DomainException.class,
                () -> slot1.nextNode("www.taobao.com"));
    }

    /**
    *
    * Method: getNodeByOrigin(origin)
    */
    @Test
    public void getNodeByOriginTest() throws Exception {
        Slot slot = new Slot("ab", 6, this.slotRepos, 100);
        Node node = slot.nextNode("www.baidu.com");
        Node node1 = slot.getNodeByOrigin("www.baidu.com");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
        assertThrows(
                IllegalArgumentException.class,
                () -> slot.getNodeByOrigin(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> slot.getNodeByOrigin(""));

        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(1);
        SlotRepos slotRepos1 = new SlotReposImpl(memorySlotRepos);
        final Slot slot1 = new Slot("abc", 6, slotRepos1, 1);
        slot1.getNodeByOrigin("www.baidu.com");
        assertThrows(
                DomainException.class,
                () -> slot1.getNodeByOrigin("www.taobao.com"));
    }

    /**
    *
    * Method: getNodeByCode(code)
    */
    @Test
    public void getNodeByCodeTest() throws Exception {
        Slot slot = new Slot("ab", 6, this.slotRepos, 100);
        Node node = slot.nextNode("www.baidu.com");
        Node node1 = slot.getNodeByCode(node.getCode());
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
        assertThrows(
                IllegalArgumentException.class,
                () -> slot.getNodeByCode(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> slot.getNodeByCode(""));
    }

}
