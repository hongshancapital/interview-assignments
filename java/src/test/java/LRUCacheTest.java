import com.example.assignment.common.LRUCache;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class LRUCacheTest {

    @Test
    public void testLRUCache() {
        LRUCache<String, String> lru = new LRUCache<>(5);
        lru.put("1", "1");
        lru.put("2", "2");
        lru.put("3", "3");
        lru.put("4", "4");
        lru.put("5", "5");
        assertEquals("1", lru.get("1"));
        lru.put("6", "6");
        assertNull(lru.get("2"));
        lru.put("7", "7");
        lru.put("8", "8");
        lru.put("9", "9");
        assertEquals("1", lru.get("1"));
    }
}
