package com.validator;

import com.TestData;
import com.AbstractTest;

import org.junit.Test;
import org.mockito.InjectMocks;

public class UrlTransformValidatorTest extends AbstractTest {

    @InjectMocks
    private UrlTransformValidator urlTransformValidator;

    @Test
    public void testValidateGetLongUrlSuccessPath() {
        urlTransformValidator.validateGetLongUrl(TestData.SHORT_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetLongUrlNullFailedPath() {
        urlTransformValidator.validateGetLongUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetLongUrlEmptyUrlFailedPath() {
        urlTransformValidator.validateGetLongUrl(TestData.EMPTY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlIllegalShortUrlWithTooManySpliterCharFailedPath() {
        urlTransformValidator.validateGetLongUrl(TestData.SHORT_URL_ILLEGAL2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlIllegalShortUrlNotBaseFailedPath() {
        urlTransformValidator.validateGetLongUrl(TestData.SHORT_URL_ILLEGAL);
    }


    @Test
    public void testValidateGetShortUrlSuccessPath() {
        urlTransformValidator.validateGetShortUrl(TestData.LONG_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlNullFailedPath() {
        urlTransformValidator.validateGetShortUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlEmptyUrlFailedPath() {
        urlTransformValidator.validateGetShortUrl(TestData.EMPTY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlTooLongUrlFailedPath() {
        urlTransformValidator.validateGetShortUrl(TestData.TOO_LONG_URL);
    }


}
