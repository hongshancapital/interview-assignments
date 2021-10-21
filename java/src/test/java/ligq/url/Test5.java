package ligq.url;

import cn.hutool.core.lang.MurmurHash;
import cn.hutool.crypto.digest.DigestUtil;
import ligq.url.dao.UrlDao;
import ligq.url.service.UrlService;
import ligq.url.util.UrlDigest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static ligq.url.MainApplicationTests.conflictUrl;

/**
 * 单元测试类
 * @author ligq
 * @since 2021-10-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test5 {

    @Resource
    private UrlService service;
    @Resource
    private UrlDao dao;

    @Test
    public void getConflictShortUrlTest() throws Exception {
        String md5 = DigestUtil.md5Hex(conflictUrl);
        int conflictId = MurmurHash.hash32(md5);
        dao.setValue(conflictId, conflictUrl + "/123");
        String shortUrl = service.getShortUrl(conflictUrl);
        Assert.assertFalse(conflictId == UrlDigest.decode(shortUrl));
    }
}
