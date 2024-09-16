package com.ttts.urlshortener.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.model.BaseResult;
import com.ttts.urlshortener.domain.LUrlReq;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.domain.ShortUrlVO;
import com.ttts.urlshortener.service.ShortUrlCreateService;
import com.ttts.urlshortener.service.UrlQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

class UrlControllerTest {

    @Test
    void shorter_shorter_null() {
        String result = null;

        ShortUrlCreateService shortUrlCreateService = mock(ShortUrlCreateService.class);
        doReturn(result)
            .when(shortUrlCreateService)
            .create(any(LUrlReq.class));

        UrlController target = new UrlController(shortUrlCreateService);

        assertThrows(BusinessException.class, () -> target.shorter(any(LUrlReq.class)));
    }

    @Test
    void shorter_throw_exception() {

        BusinessException mockException = mock(BusinessException.class);

        ShortUrlCreateService shortUrlCreateService = mock(ShortUrlCreateService.class);
        doThrow(mockException)
            .when(shortUrlCreateService)
            .create(any(LUrlReq.class));

        UrlController target = new UrlController(shortUrlCreateService);

        assertThrows(BusinessException.class, () -> target.shorter(any(LUrlReq.class)));
    }

    @Test
    void shorter_return_ok() {
        String lurl = "12344";

        LUrlReq req = mock(LUrlReq.class);
        doReturn(lurl).when(req).getLurl();

        ShortUrlVO result = mock(ShortUrlVO.class);

        ShortUrlCreateService shortUrlCreateService = mock(ShortUrlCreateService.class);
        doReturn(result)
            .when(shortUrlCreateService)
            .create(req);

        UrlController target = new UrlController(shortUrlCreateService);

        BaseResult<ShortUrlVO> except = BaseResult.success(result);
        BaseResult<ShortUrlVO> actual = target.shorter(req);

        assertEquals(except, actual);
    }
}