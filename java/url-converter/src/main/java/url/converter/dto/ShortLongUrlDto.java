package url.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLongUrlDto {

    private String shortUrl;

    private String longUrl;
}
