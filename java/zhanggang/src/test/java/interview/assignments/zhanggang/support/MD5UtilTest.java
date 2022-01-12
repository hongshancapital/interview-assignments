package interview.assignments.zhanggang.support;

import interview.assignments.zhanggang.config.exception.base.SystemException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MD5UtilTest {

    @Test
    void test_convert_string_to_md5() {
        final String url = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        final String md5 = MD5Util.md5(url);
        assertThat(md5).isEqualTo("8755c11a95717e2b992128b253c097c4");
    }

    @Test
    void test_convert_string_failed() {
        try (MockedStatic<MessageDigest> messageDigest = Mockito.mockStatic(MessageDigest.class)) {
            messageDigest.when(() -> MessageDigest.getInstance("MD5")).thenThrow(new NoSuchAlgorithmException());

            assertThatThrownBy(() -> MD5Util.md5("test"))
                    .isInstanceOf(SystemException.class);
        }
    }
}