import com.example.assignment.utils.MatchUtil;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MatchUtilTest {
    @Test
    public void testIsShortCodeLegal() {
        assertFalse(MatchUtil.isShortCodeLegal("82ag#AZU"));
        assertTrue(MatchUtil.isShortCodeLegal("82ag77AZU"));
        assertFalse(MatchUtil.isShortCodeLegal("82ag77777AZU"));
        assertTrue(MatchUtil.isShortCodeLegal("82ag7777AZU"));
    }

    @Test
    public void testIsUrlLegal() {
        assertFalse(MatchUtil.isUrlLegal("www.baidu.com"));
        assertTrue(MatchUtil.isUrlLegal("https://www.baidu.com/"));
        assertFalse(MatchUtil.isUrlLegal("wwwbaiducom"));
        assertTrue(MatchUtil.isUrlLegal("http://s.weibo.com/weibo?q=%23%E5%9B%BD%E5%8F%B0%E5%8A%9E%E5%9B%9E%E5%BA%94%E5%8F%B0%E6%B9%BE%E8%89%BA%E4%BA%BA%E7%A5%9D%E7%A6%8F%E7%A5%96%E5%9B%BD%E9%81%AD%E6%81%90%E5%90%93%23&from=default"));
    }
}
