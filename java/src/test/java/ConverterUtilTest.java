import com.example.assignment.utils.ConverterUtil;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ConverterUtilTest {

    @Test
    public void testEncode10To62() {
        long num = 1111L;
        String result = ConverterUtil.encode10To62(num, 8);
        assertEquals("000000hV", result);
    }

    @Test
    public void testEncode10To62_2() {
        long num = 111111111111111L;
        String result = ConverterUtil.encode10To62(num, 8);
        assertEquals("vyaKCTiv", result);
    }
}
