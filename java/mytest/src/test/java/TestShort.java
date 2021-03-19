import my.test.ShortUrlTool;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestShort {
    @Test
    public void test() {
            String url = "https://a.b.com/js6/main.jsp?sid=EAPbrAbAxLMXphZiWFAAfXTeRPnEwRBt&df=unknow#module=mbox.ListModule%7C%7B%22filter%22%3A%7B%22flags%22%3A%7B%22read%22%3Afalse%7D%7D%2C%22order%22%3A%22date%22%2C%22desc%22%3Atrue%2C%22fids%22%3A%5B1%2C3%5D%7D";
            String shortUrl = ShortUrlTool.urlTransferShortUrl(url, 10);
            assertEquals(ShortUrlTool.ShortUrlTransferUrl(shortUrl), url);
    }
}
