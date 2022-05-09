package com.example.assignment.rest;

import com.example.assignment.common.LinkCommon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ShortController {
    /**
     *  可以使用26个英文字母，划分不同的业务
     *
     * @param shortLinkCode 短链接code
     * @param response 返回信息
     * @throws IOException
     */
    @GetMapping("{s}/{shortLinkCode}")
    public void redirect(@PathVariable("shortLinkCode") String shortLinkCode, HttpServletResponse response) throws IOException {
        String fullLink = LinkCommon.shortLinkMap.get(shortLinkCode);
        response.sendRedirect(fullLink);
    }
}
