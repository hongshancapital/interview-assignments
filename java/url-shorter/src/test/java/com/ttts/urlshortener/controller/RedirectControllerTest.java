package com.ttts.urlshortener.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.service.UrlQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

class RedirectControllerTest {

    @Test
    void redirect_return_null() {
        String result = null;

        UrlQueryService urlQueryService = mock(UrlQueryService.class);
        doReturn(result)
            .when(urlQueryService)
            .queryLurl(anyString());

        RedirectController target = new RedirectController(urlQueryService);

        assertThrows(BusinessException.class, () -> target.redirect(anyString()));
    }

    @Test
    void redirect_throw_exception() {
        BusinessException mockException = mock(BusinessException.class);

        UrlQueryService urlQueryService = mock(UrlQueryService.class);
        doThrow(mockException)
            .when(urlQueryService)
            .queryLurl(anyString());

        RedirectController target = new RedirectController(urlQueryService);

        assertThrows(BusinessException.class, () -> target.redirect(anyString()));
    }

    @Test
    void redirect_return_ok() {
        String result = "http:www.123.com/egh";

        UrlQueryService urlQueryService = mock(UrlQueryService.class);
        doReturn(result)
            .when(urlQueryService)
            .queryLurl(anyString());

        RedirectController target = new RedirectController(urlQueryService);
        ResponseEntity actual = target.redirect(anyString());

        assertEquals(result, actual.getHeaders().getLocation().toString());
    }
}