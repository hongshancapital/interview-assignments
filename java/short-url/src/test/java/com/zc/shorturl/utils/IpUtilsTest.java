package com.zc.shorturl.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.SocketException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class IpUtilsTest {

    @Test
    public void testGetIp() {
        assertThat(IpUtils.getIp(), not(""));
        try (MockedStatic<IpUtils> theMock = Mockito.mockStatic(IpUtils.class)) {
            theMock.when(() -> IpUtils.getHostAddress(null)).thenThrow(new SocketException());
            theMock.when(IpUtils::getIp).thenCallRealMethod();
            assertThat(IpUtils.getIp(), equalTo(""));

            theMock.reset();
            theMock.when(() -> IpUtils.getHostAddress(null)).thenReturn(new ArrayList<String>());
            theMock.when(IpUtils::getIp).thenCallRealMethod();
            assertThat(IpUtils.getIp(), equalTo(""));
        }
    }

    @Test
    public void testGetHostAddress() {
        try {
            assertThat(IpUtils.getHostAddress(null).size(), greaterThan(0));
            assertThat(IpUtils.getHostAddress("Intel(R) Ethernet Connection (5) I219-LM").size(), greaterThan(0));
        }catch (Exception e){

        }

    }
}
