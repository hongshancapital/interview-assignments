package com.example.shortlink.application;

import com.example.shortlink.application.rest.ShortLinkController;
import com.example.shortlink.domain.entity.ShortLink;
import com.example.shortlink.domain.service.ShortLinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(ShortLinkController.class)
@ComponentScan(basePackages = "com.example.shortlink.application.rest")
public class ShortLinkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShortLinkService shortLinkServiceMock;

    @Test
    public void should_success_when_gen_shortLink() throws Exception{
        ShortLink shortLink = ShortLink.builder().build();
        shortLink.setShortLinkKey("12343");
        shortLink.setLongLink("https://github.com/Netflix/concurrency-limits");
        when(shortLinkServiceMock.genShortLink(any(String.class))).thenReturn(shortLink);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/s/").param("shortLinkKey","https://github.com/Netflix/concurrency-limits");
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("12343")));
    }
    @Test
    public void should_fail_when_genShortLinkWithNull() throws Exception{
        MockHttpServletRequestBuilder builder = post("/s/");
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_fail_when_genShortLinkWithIllegleUrl() throws Exception{
        String contents = "123124";
        MockHttpServletRequestBuilder builder = post("/s/").param("shortLinkKey",contents);
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("the content is not a url")));
    }
    @Test
    public void should_success_when_get_correct_shortLink()throws Exception {
        when(shortLinkServiceMock.getSourceLink(any(String.class))).thenReturn("https://github.com/Netflix/concurrency-limits");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/s/12434");
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("https://github.com/Netflix/concurrency-limits")));
    }
    @Test
    public void should_fail_when_get_wrong_shortLink()throws Exception {
        when(shortLinkServiceMock.getSourceLink(any(String.class))).thenReturn(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/s/12434");
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("null")));
    }

}
