package com.assignment;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(PowerMockRunner.class)
public abstract class BaseTest {

    @Before
    public void before() {
        openMocks(this);
    }
}
