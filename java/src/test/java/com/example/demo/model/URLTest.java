package com.example.demo.model;

import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import nl.jqno.equalsverifier.EqualsVerifier;

class URLTest {

    private URL url;

    @BeforeEach
    public void setUp() throws Exception {
        url = new URL();
        url.setId(1);
        url.setShortURL("a");
        url.setOriginalURL("www.baidu.com");
    }

    @AfterEach
    public void clear() {
        url = null;
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(url.getClass()).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void testToString() {
        URL url1 = new URL();
        url1.setId(1);
        url1.setShortURL("a");
        url1.setOriginalURL("www.baidu.com");
        String str = "URL(id=1, shortURL=a, originalURL=www.baidu.com)";
        assertThat(str, equalTo(url1.toString()));
    }
}