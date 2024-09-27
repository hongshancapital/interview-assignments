package com.wf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void getUserDocket() {

        SwaggerConfig sc = new SwaggerConfig();
        sc.getUserDocket();
    }
}