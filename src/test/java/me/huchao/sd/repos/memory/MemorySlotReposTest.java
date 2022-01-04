package me.huchao.sd.repos.memory;

import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.domain.url.repos.MockSlotRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
* <p> MemorySlotRepos Tester. </p>
* <p> 2021-12-30 12:29:48.542 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class MemorySlotReposTest {

    private String slotName;


    @BeforeEach
    public void setup() {
        this.slotName = "test";
    }

    /**
    *
    * Method: MemorySlotRepos()
    */
    @Test
    public void MemorySlotReposTest() throws Exception {
        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(10000);
        assertNotNull(memorySlotRepos);
    }

    /**
    *
    * Method: prefetchOffset(slotName, preFetchOffsetSize)
    */
    @Test
    public void prefetchOffsetTest() throws Exception {
        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(10000);
        long offset = memorySlotRepos.prefetchOffset(this.slotName, 100);
        assertEquals(100, offset);
        offset = memorySlotRepos.prefetchOffset(this.slotName, 100);
        assertEquals(200, offset);
    }

    /**
    *
    * Method: insertNode(node)
    */
    @Test
    public void insertNodeTest() throws Exception {
        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(10000);
        Node node = new Node(this.slotName, 1, "testOrigin", "testCode");
        memorySlotRepos.insertNode(node);
        Node node1 = memorySlotRepos.getNodeByOrigin(this.slotName, "testOrigin");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }

    /**
    *
    * Method: getNodeByOrigin(slotName, origin)
    */
    @Test
    public void getNodeByOriginTest() throws Exception {
        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(10000);
        Node node = new Node(this.slotName, 1, "testOrigin", "testCode");
        memorySlotRepos.insertNode(node);
        Node node1 = memorySlotRepos.getNodeByOrigin(this.slotName, "testOrigin");
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
        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(10000);
        Node node = new Node(this.slotName, 1, "testOrigin", "testCode");
        memorySlotRepos.insertNode(node);
        Node node1 = memorySlotRepos.getNodeByCode(this.slotName, "testCode");
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }
}
