package com.example.demo.response;

import com.example.demo.request.QueryOriginalURLRequest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class BaseResponseTest {

    private BaseResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        response = new BaseResponse();
        response.setCode(1);
        response.setMessage("sddd");
        response.setSuccess(true);
        response.setData("hello");
    }

    @AfterEach
    public void clear() {
        response = null;
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(response.getClass()).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void error() {
        BaseResponse response = BaseResponse.error(ResultCode.NOT_FOUND);
        assertThat(true, equalTo(response.getCode() == ResultCode.NOT_FOUND.getCode()));
    }

    @Test
    void canEqual() {
        BaseResponse response1 = new BaseResponse();
        BaseResponse response2 = new BaseResponse();
        assertThat(true, equalTo(response1.equals(response2)));
    }

    @Test
    void testToString() {
        BaseResponse response1 = new BaseResponse();
        response1.setCode(1);
        String str = "BaseResponse(success=true, code=1, message=操作成功！, data=null)";
        assertThat(str, equalTo(response1.toString()));
    }
}