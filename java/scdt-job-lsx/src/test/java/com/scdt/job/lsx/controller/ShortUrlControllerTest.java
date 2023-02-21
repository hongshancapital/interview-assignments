package java.com.scdt.job.lsx.controller;

import com.scdt.job.lsx.controller.ShortUrlController;
import com.scdt.job.lsx.model.ResponseResult;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@SpringBootTest
class ShortUrlControllerTest {

    @Resource
    private ShortUrlController shortUrlController;

    /**
     * 长链转短链
     */
    @Test
    void nativeToShort() {
        ResponseResult<String> shortUrlResult = shortUrlController.nativeToShort("https://www.baidu.com/s?wd=%E7%9C%9F%E7%9A%84%20%20%E5%8A%A0%E7%9A%84%20%20%20%E6%98%AF%E7%9A%84%20%20%E6%88%91%E6%93%A6%20%E6%92%92%E5%A4%A7%E5%A3%B0%E5%9C%B0%E5%9B%9B%E5%A4%A7%E7%9A%86%E7%A9%BA%E6%94%BE%E5%A3%B0%E9%AB%98%E6%AD%8C%20%20%E6%96%B9%E5%8F%91%E7%94%9F%E7%9A%84&rsv_spt=1&rsv_iqid=0xd47cfeb800121fc3&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&rsv_dl=tb&oq=%25E7%259C%259F%25E7%259A%2584%2520%25E5%258A%25A0%25E7%259A%2584%2520%25E6%2598%25AF%25E7%259A%2584%2520%25E6%2588%2591%25E6%2593%25A6&rsv_btype=t&inputT=5583&rsv_t=b410SItmVJpR54xizpKypnGvrg3n%2F8rJH%2BxUNvwHe8vHoQdlrdcriAlca4RKlkQy4433&rsv_sug3=54&rsv_sug1=27&rsv_sug7=100&rsv_pq=b07557b700343331&rsv_sug4=5716");

        Assert.notNull(shortUrlResult, "生成短链接不能为空");
    }

    /**
     * 短链转长链，短链存在
     */
    @Test
    void shortToNative() {
        ResponseResult<String> shortUrlResult1 = shortUrlController.nativeToShort("www.baidu.com");
        Assert.notNull(shortUrlResult1, "长链转短链响应不能为空");
        Assert.notNull(shortUrlResult1.getData(), "生成短链接不能为空");

        ResponseResult<String> longUrlResult1 = shortUrlController.shortToNative(shortUrlResult1.getData());
        Assert.notNull(longUrlResult1, "短链转长链响应不能为空");
        Assert.notNull(longUrlResult1.getData(), "查询短链失败");

    }

    /**
     * 短链转长链，短链不存在
     */
    @Test
    void shortToNative2() {
        ResponseResult<String> longUrlResult2 = shortUrlController.shortToNative("XXXXXXXX");
        Assert.notNull(longUrlResult2, "短链转长链响应不能为空");
        Assert.isTrue(Strings.EMPTY.equals(longUrlResult2.getData()), "查询结果应该为空");
    }

}