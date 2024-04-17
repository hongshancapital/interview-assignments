package cn.sequoiacap.links;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Liushide
 * @date :2022/4/5 16:58
 * @description : 主启动类测试
 */
class LinksApplicationTest {

    @Test
    @DisplayName("测试 LinksApplication 类的 main 方法")
    void mainTest() {
        boolean flag = true;
        LinksApplication.main(new String[]{});
        assertEquals(true, flag);
    }

}