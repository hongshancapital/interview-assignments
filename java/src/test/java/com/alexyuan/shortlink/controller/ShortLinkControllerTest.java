package com.alexyuan.shortlink.controller;

import com.alexyuan.shortlink.common.variant.ResultVariant;
import com.alexyuan.shortlink.exceptions.ImproperValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortLinkControllerTest {

    @Resource
    ShortLinkController shortLinkController;

    private String long_link = "http://www.alexkxyuan.com/test/shortlinkconverter";

    @Test
    public void testCases() {
        ResultVariant resultVariant = shortLinkController.genShortLink(long_link);
        assertThat(resultVariant.getLong_url()).isEqualTo(long_link);
        assertThat(resultVariant.getShort_url()).isNotNull();
        assertThat(resultVariant.getShort_url()).isNotEmpty();
        assertThat(shortLinkController.getLongLink(resultVariant.getLong_url()).getLong_url()).isEqualTo(long_link);
        assertThat(resultVariant.getResponse_code()).isEqualTo("200");

        resultVariant = shortLinkController.genShortLink(null);
        assertThat(resultVariant.getResponse_code()).isEqualTo("300");
        assertThat(resultVariant.getLong_url()).isEqualTo(null);
        assertThat(resultVariant.getShort_url()).isEmpty();

        resultVariant = shortLinkController.genShortLink(long_link);
        assertThat(shortLinkController.getLongLink(resultVariant.getShort_url().substring(0, 1)).getLong_url())
                .isNotEqualTo(long_link);

        resultVariant = shortLinkController.getLongLink(null);
        assertThat(resultVariant.getResponse_code()).isEqualTo("300");
        assertThat(resultVariant.getLong_url()).isEmpty();
        assertThat(resultVariant.getShort_url()).isEqualTo(null);

        resultVariant = shortLinkController.genShortLinkPress(long_link);
        assertThat(resultVariant.getLong_url()).isEqualTo(long_link);
        assertThat(resultVariant.getShort_url()).isNotNull();
        assertThat(resultVariant.getShort_url()).isNotEmpty();
        assertThat(shortLinkController.getLongLink(resultVariant.getLong_url()).getLong_url()).isEqualTo(long_link);
        assertThat(resultVariant.getResponse_code()).isEqualTo("200");

        resultVariant = shortLinkController.genShortLinkPress(null);
        assertThat(resultVariant.getResponse_code()).isEqualTo("300");
        assertThat(resultVariant.getLong_url()).isEqualTo(null);
        assertThat(resultVariant.getShort_url()).isEmpty();

        resultVariant = shortLinkController.genShortLinkPress(long_link);
        assertThat(shortLinkController.getLongLink(resultVariant.getShort_url().substring(0, 1)).getLong_url())
                .isNotEqualTo(long_link);
    }

}
