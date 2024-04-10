package city.yyds.job.hsdemo.controller;

import city.yyds.job.hsdemo.service.ShortUrlService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

    private final Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private ShortUrlService shortUrlService;

    @RequestMapping(value = "getShortUrl",method = RequestMethod.GET)
    public String getShortUrl(@ApiParam(value = "url",required = true,defaultValue="")  @RequestParam(value = "url")String url){
        return shortUrlService.getShortUrl(url);
    }
}
