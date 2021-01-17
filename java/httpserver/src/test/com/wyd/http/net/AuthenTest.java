package com.wyd.http.net;

import com.wyd.http.server.constants.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;

public class AuthenTest {

    @Test
    public void testuthen() {
        byte[] encode = Base64.getEncoder().encode(Constants.BASE64_KEY.getBytes());
        byte[] decode = Base64.getDecoder().decode(encode);
        Assert.assertEquals(new String(decode), Constants.BASE64_KEY);
    }
}
