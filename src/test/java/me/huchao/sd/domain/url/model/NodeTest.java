package me.huchao.sd.domain.url.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* <p> Node Tester. </p>
* <p> 2021-12-29 12:30:47.920 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class NodeTest {

    /**
    *
    * Method: Node(slotName, offset, origin, code)
    */
    @Test()
    public void NodeTest() throws Exception {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Node(null, 1, "origin", "code"));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Node("", 1, "origin", "code"));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Node("name", -1, "origin", "code"));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Node("name", 1, null, "code"));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Node("name", 1, "origin", null));
        Node node = new Node("name", 1, "origin", "code");
        assertEquals("name", node.getSlotName());
        assertEquals(1, node.getOffset());
        assertEquals("origin", node.getOrigin());
        assertEquals("code", node.getCode());
    }
}
