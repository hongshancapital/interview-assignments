package interview.assignments.zhanggang.support;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

class MD5UtilTest {

    @Test
    void test_convert_string_to_md5() throws NoSuchAlgorithmException {
        final String url = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        final String md5 = MD5Util.md5(url);
        assertThat(md5).isEqualTo("8755c11a95717e2b992128b253c097c4");
    }
}