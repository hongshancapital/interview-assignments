package red.lilu.service.api;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.codec.digest.MurmurHash3;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Api(tags = "短网址")
public class ShortUrlController {

  /**
   * 未考虑可配置性
   */
  private static final String ShortDomain = "https://l.r/";
  private static final ConcurrentHashMap<String, String> SuMap = new ConcurrentHashMap<>();

  /**
   * 使用MurmurHash计算编码
   *
   * @param txt URL
   * @return 编码
   */
  private static String toCode(String txt) {
    int hash = Math.abs(
        MurmurHash3.hash32x86(
            txt.getBytes(StandardCharsets.UTF_8)
        )
    );
    String hashText = String.valueOf(hash);

    int length = hashText.length();
    if (length > 8) {
      hashText = hashText.substring(0, 8);
    } else if (length < 8) {
      hashText = String.valueOf(hash * Math.pow(10, 8 - length));
    }

    return hashText;
  }

  @GetMapping("/")
  @ApiOperation("首页")
  public String home() {
    return "短网址服务";
  }

  @GetMapping("/to")
  @ResponseHeader(name = "Content-Type", description = "text/plain")
  @ApiOperation("转短域名")
  @ApiResponses({
      @ApiResponse(code = 200, message = "短域名", examples = @Example({
          @ExampleProperty(mediaType = "text/plain", value = "https://l.r/12345678")
      }))
  })
  public ResponseEntity<String> to(@RequestParam(value = "url") @Parameter(description = "长域名") String url) {
    String code = toCode(url);

    // 保证唯一
    if (SuMap.containsKey(code)) {
      code = toCode(UUID.randomUUID().toString());
    }

    // 缓存
    SuMap.put(code, url);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.TEXT_PLAIN);
    return new ResponseEntity<>(ShortDomain + code, responseHeaders, HttpStatus.OK);
  }

  @GetMapping("/from")
  @ResponseHeader(name = "Content-Type", description = "text/plain")
  @ResponseBody
  @ApiOperation("取长域名")
  @ApiResponses({
      @ApiResponse(code = 400, message = "短域名无效"),
      @ApiResponse(code = 200, message = "长域名", examples = @Example({
          @ExampleProperty(mediaType = "text/plain", value = "https://lilu.red/app/ne/")
      }))
  })
  public ResponseEntity<String> from(@RequestParam(value = "su") @Parameter(description = "短域名") String su) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.TEXT_PLAIN);

    String url = SuMap.get(su.replaceFirst(ShortDomain, ""));

    // 检查
    if (url == null) {
      return new ResponseEntity<>("短域名无效", responseHeaders, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(url, responseHeaders, HttpStatus.OK);
  }

}
