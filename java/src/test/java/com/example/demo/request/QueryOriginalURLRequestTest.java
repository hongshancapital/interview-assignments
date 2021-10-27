package com.example.demo.request;

import com.example.demo.model.URL;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class QueryOriginalURLRequestTest {

    private QueryOriginalURLRequest request;

    @BeforeEach
    public void setUp() throws Exception {
        request = new QueryOriginalURLRequest();
        request.setShortURL("zbc");
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
        QueryOriginalURLRequest request1 = new QueryOriginalURLRequest();
        QueryOriginalURLRequest request2 = new QueryOriginalURLRequest();
        assertThat(true, equalTo(request1.equals(request2)));
    }

    @Test
    void testToString() {
        QueryOriginalURLRequest request1 = new QueryOriginalURLRequest();
        request1.setShortURL("aa");
        String str = "QueryOriginalURLRequest(shortURL=aa)";
        assertThat(str, equalTo(request1.toString()));
    }
}