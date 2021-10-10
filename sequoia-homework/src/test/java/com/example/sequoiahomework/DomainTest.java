package com.example.sequoiahomework;

import com.example.sequoiahomework.common.response.DataResult;
import com.example.sequoiahomework.vo.url.ChangeUrlVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Irvin
 * @description 实体测试
 * @date 2021/10/10 17:32
 */
@RunWith(SpringRunner.class)
@Slf4j
public class DomainTest {

    @Test
    public void testResult() {
        DataResult<String> vo = new DataResult<>(1,200,"a", "a");
        log.info("vo{}", vo.toString());

        DataResult<String> vo2 = new DataResult<>();
        log.info("vo2{}", vo2.toString());

        log.info("实体比对结果{}", vo.equals(vo2));
    }

    @Test
    public void testVo() {
        ChangeUrlVo vo = new ChangeUrlVo();
        vo.setOriginalUrl("sadsa");
        log.info("vo{}", vo.toString());

        ChangeUrlVo vo2 = new ChangeUrlVo();
        vo2.setOriginalUrl("sadsa");
        log.info("vo2{}", vo2.toString());

        log.info("实体比对结果{}", vo.equals(vo2));
    }
}
