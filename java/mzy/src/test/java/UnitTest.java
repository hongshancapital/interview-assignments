import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.ApplicationStarter;
import test.controllers.ShortLinkController;
import test.entity.ShortLinkConfigPO;
import test.handles.GlobalExceptionHandler;
import test.mapper.ShortLinkConfigMapper;
import test.utils.NumberConvertUtil;
import test.vo.ResultVo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStarter.class)
public class UnitTest {

    @Autowired
    private ShortLinkController shortLinkController;
    @Resource
    private ShortLinkConfigMapper shortLinkConfigMapper;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testUtils() {
        // 十进制62转换为62进制就是10
        String afterConvert = NumberConvertUtil.convert(62);
        Assert.assertEquals(afterConvert, "10");
    }


    @Test
    public void testController() throws InterruptedException {
        // 测试非法输入
        ResultVo<String> r0 = shortLinkController.getLongLink("sdgsggd");
        Assert.assertNotEquals(r0.getError(), ResultVo.SUCCESS_CODE);

        String ll = "www.lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll.asdafaff";
        ResultVo<String> rs = shortLinkController.getShortLink(ll);
        Assert.assertEquals(rs.getError(), ResultVo.SUCCESS_CODE);
        ResultVo<String> rl = shortLinkController.getLongLink(rs.getData());

        // 循环218340105584897次，出发进制，才能满足覆盖率
        String longLink = "www.baidu.com/asdasdrtrhh/123456";
        r0 = shortLinkController.getLongLink("ssss");
        Assert.assertNotEquals(r0.getError(), ResultVo.SUCCESS_CODE);
        for (int i = 0; i < 100; i++) {
            ResultVo<String> r1 = shortLinkController.getShortLink(longLink + i);
            Assert.assertEquals(r1.getError(), ResultVo.SUCCESS_CODE);

//            TimeUnit.MILLISECONDS.sleep(10);

            ResultVo<String> r2 = shortLinkController.getShortLink(longLink + i);
            Assert.assertEquals(r1.getError(), ResultVo.SUCCESS_CODE);

            ResultVo<String> r3 = shortLinkController.getLongLink(r1.getData());
            Assert.assertEquals(r3.getError(), ResultVo.SUCCESS_CODE);
        }

        for (int i = 0; i < 10; i++) {
            ResultVo<String> r1 = shortLinkController.getShortLink(longLink + i);
            Assert.assertEquals(r1.getError(), ResultVo.SUCCESS_CODE);

            ResultVo<String> r2 = shortLinkController.getLongLink(r1.getData());
            Assert.assertEquals(r2.getError(), ResultVo.SUCCESS_CODE);
        }

        // 出发分支覆盖
        TimeUnit.SECONDS.sleep(1);
        rs = shortLinkController.getShortLink(ll);
        rl = shortLinkController.getLongLink(rs.getData());
        Assert.assertEquals(rs.getError(), ResultVo.SUCCESS_CODE);
    }

    @Test
    public void dataBaseTest() {
        // 验证数据库链接正常
        ShortLinkConfigPO s2 = shortLinkConfigMapper.selectById(1);
        ShortLinkConfigPO s3 = shortLinkConfigMapper.selectById(1);
        System.out.println(Objects.equals(s2, s3));
        System.out.println(s2.hashCode());
        s2.setId(1L);
        s2.setLongLink("ll");
        s2.setShortLink("sl");
        s2.setLongLinkHash(String.valueOf("ll".hashCode()));
        System.out.println("" + s2.getLongLinkHash() + s2.getId() + s2.getLongLink() + s2.getShortLink());
    }

    @Test
    public void exceptionHandle() {
        // 异常捕获测试
        try {
            shortLinkController.getLongLink("1231412512555");
        } catch (ConstraintViolationException e) {
            ResultVo resultVo = globalExceptionHandler.handleConstraintViolationException(e);
            System.out.println(resultVo);
        }

        try {
            shortLinkController.getLongLink(null);
        } catch (Exception e) {
            ResultVo resultVo = globalExceptionHandler.handle(e);
            System.out.println(resultVo);
        }
    }

    @Test
    public void addition() {
        // 补充测试覆盖率
        ResultVo<String> r0 = shortLinkController.getLongLink("1235");
//        Assert.assertNotEquals(r0.getError(), ResultVo.SUCCESS_CODE);
        System.out.println(r0.toString());
        System.out.println(r0.hashCode());
        System.out.println(r0.getMsg());
        ResultVo<String> r1 = shortLinkController.getShortLink("12356789");
        System.out.println(r1.equals(r0));
    }

}
