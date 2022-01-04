package me.huchao.sd.domain.url.manager;

import me.huchao.sd.domain.url.model.Bucket;
import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.domain.url.repos.MockSlotRepos;
import me.huchao.sd.domain.url.repos.SlotRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* <p> BucketManager Tester. </p>
* <p> 2021-12-30 12:29:48.550 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
public class BucketManagerTest {

    private SlotRepos slotRepos;

    private int preFetchOffsetSize;

    @BeforeEach
    public void setup() {
        this.slotRepos = new MockSlotRepos();
        this.preFetchOffsetSize = 1000;
    }

    /**
    *
    * Method: BucketManager(bucket)
    */
    @Test
    public void BucketManagerTest() throws Exception {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BucketManager(null));
        Bucket bucket = new Bucket(this.slotRepos, this.preFetchOffsetSize, 10);
        BucketManager bucketManager = new BucketManager(bucket);
    }

    /**
    *
    * Method: getByCode(code)
    */
    @Test
    public void getByCodeTest() throws Exception {
        Bucket bucket = new Bucket(this.slotRepos, this.preFetchOffsetSize, 10);
        BucketManager bucketManager = new BucketManager(bucket);
        assertThrows(
                IllegalArgumentException.class,
                () -> bucketManager.getByCode(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> bucketManager.getByCode(""));
        Node node = bucketManager.getByOrigin("testOrigin");
        String code = node.getCode();
        Node node1 = bucketManager.getByCode(code);
        assertEquals(node.getSlotName(), node1.getSlotName());
        assertEquals(node.getCode(), node1.getCode());
        assertEquals(node.getOrigin(), node1.getOrigin());
        assertEquals(node.getOffset(), node1.getOffset());
    }

    /**
    *
    * Method: getByOrigin(origin)
    */
    @Test
    public void getByOriginTest() throws Exception {
        Bucket bucket = new Bucket(this.slotRepos, this.preFetchOffsetSize, 10);
        BucketManager bucketManager = new BucketManager(bucket);
        assertThrows(
                IllegalArgumentException.class,
                () -> bucketManager.getByOrigin(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> bucketManager.getByOrigin(""));
        Node node = bucketManager.getByOrigin("testOrigin");
        assertEquals("testOrigin", node.getOrigin());
    }

}
