package com.alexyuan.shortlink.common.functions;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@RunWith(MockitoJUnitRunner.class)
public class AutoIncreNumSenderTest {

    @Test
    public void testGeneration() {
        AutoIncreNumSender autoIncreNumSender = new AutoIncreNumSender(1L, 10L);
        assertThat(autoIncreNumSender.getCol_num()).isEqualTo(1L);
    }

    @Test
    public void testGenerateFunctionWorking() {
        AutoIncreNumSender autoIncreNumSender = new AutoIncreNumSender(1L, 10L, 10L);
        assertThat(autoIncreNumSender.generateNum()).isEqualTo(10L);
    }

    @Test
    public void testGenerateFunctionNotWorking() {
        AutoIncreNumSender autoIncreNumSender = new AutoIncreNumSender(1L, 10L, 11L);
        Assert.assertNull( autoIncreNumSender.generateNum());
    }
}
