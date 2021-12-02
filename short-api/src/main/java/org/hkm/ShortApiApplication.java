package org.hkm;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableConfigurationProperties(value={AppConfigProperties.class})
public class ShortApiApplication {


    public static void main(String[] args) throws UnsupportedEncodingException {

//        String h = "http%3A%2F%2Flocalhost%3A8080%2Fc7rFxEs7";
//
//        Pattern pattern = Pattern.compile("/\\w{8}$");
//
//        System.out.println(pattern.matcher(h).group());


        new SpringApplicationBuilder(ShortApiApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
