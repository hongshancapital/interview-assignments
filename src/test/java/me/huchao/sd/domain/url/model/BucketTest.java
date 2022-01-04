package me.huchao.sd.domain.url.model;

import me.huchao.sd.domain.url.repos.MockSlotRepos;
import me.huchao.sd.domain.url.repos.SlotRepos;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* <p> Bucket Tester. </p>
* <p> 2021-12-29 12:30:47.918 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class BucketTest {

    private SlotRepos slotRepos;

    private int preFetchOffsetSize;

    @BeforeEach
    public void setup() {
        this.slotRepos = new MockSlotRepos();
        this.preFetchOffsetSize = 10000;
    }
    /**
     *
     * Method: getByOrigin(origin)
     */
    @Test
    public void BucketTest() throws Exception {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Bucket(null, 6, 1));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Bucket(this.slotRepos, 0, 1));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Bucket(this.slotRepos, -1, 1));

    }


    /**
     *
     * Method: getByOrigin(origin)
     */
    @Test
    public void getByOriginTest() throws Exception {
        Bucket bucket = new Bucket(this.slotRepos, this.preFetchOffsetSize, 10);
        String origin = "www.baidu.com";
        String md5 = DigestUtils.md5Hex(origin);
        Node node = bucket.getByOrigin(origin);
        assertEquals(md5.substring(0, 2), node.getSlotName());
        assertEquals(1000, node.getOffset());
        assertEquals(origin, node.getOrigin());
        assertEquals(md5.substring(0, 2) + "0000fE", node.getCode());
        Node node1 = bucket.getByOrigin(origin);
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
        origin = "www.taobao.com";
        md5 = DigestUtils.md5Hex(origin);
        node = bucket.getByOrigin(origin);
        assertEquals(md5.substring(0, 2), node.getSlotName());
        assertEquals(1000, node.getOffset());
        assertEquals(origin, node.getOrigin());
        assertEquals(md5.substring(0, 2) + "0000fE", node.getCode());
        assertThrows(
                IllegalArgumentException.class,
                () -> bucket.getByOrigin(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> bucket.getByOrigin(""));

    }

    /**
    *
    * Method: getByShortDomain(shortDomain)
    */
    @Test
    public void getByShortDomainTest() throws Exception {
        Bucket bucket = new Bucket(this.slotRepos, this.preFetchOffsetSize, 10);
        String origin = "www.baidu.com";
        Node node = bucket.getByOrigin(origin);
        String code = node.getCode();
        Node node1 = bucket.getByCode(code);
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
        assertThrows(
                IllegalArgumentException.class,
                () -> bucket.getByCode(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> bucket.getByCode(""));
    }


}
