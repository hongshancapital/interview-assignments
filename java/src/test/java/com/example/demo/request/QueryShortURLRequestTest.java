package com.example.demo.request;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class QueryShortURLRequestTest {

    private QueryShortURLRequest request;

    @BeforeEach
    public void setUp() throws Exception {
        request = new QueryShortURLRequest();
        request.setOriginUrl("https://www.baidu.com");
    }

    @AfterEach
    public void clear() {
        request = null;
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(request.getClass()).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void canEqual() {
        QueryShortURLRequest request1 = new QueryShortURLRequest();
        QueryShortURLRequest request2 = new QueryShortURLRequest();
        assertThat(true, equalTo(request1.equals(request2)));
    }

    @Test
    void testToString() {
        QueryShortURLRequest request1 = new QueryShortURLRequest();
        request1.setOriginUrl("https://www.baidu.com");
        String str = "QueryShortURLRequest(originUrl=https://www.baidu.com)";
        assertThat(str, equalTo(request1.toString()));
    }
}