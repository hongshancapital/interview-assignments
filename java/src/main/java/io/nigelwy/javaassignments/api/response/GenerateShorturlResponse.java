package io.nigelwy.javaassignments.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateShorturlResponse {
    @Schema(example = "uag8ea", type = "string", description = "生成的url短链接")
    private String shortUrl;
    @Schema(example = "https://www.qq.com", type = "string", description = "原始的url长链接")
    private String originUrl;
}
