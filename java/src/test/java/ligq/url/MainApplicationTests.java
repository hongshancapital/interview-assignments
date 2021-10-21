package ligq.url;

import cn.hutool.core.util.IdUtil;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 单元测试类
 * @author ligq
 * @since 2021-10-21
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Test1.class, Test2.class, Test3.class, Test4.class, Test5.class})
public class MainApplicationTests {

    public static final String testUrl = "https://www.yiche.com/topics/xxhd/210901lmlk/" + IdUtil.simpleUUID();
    public static String shortUrl = null;
    public static final String conflictUrl =  "http://test.test/" +  IdUtil.simpleUUID();
}
