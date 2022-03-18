package com.yofei.shortlink.dto.request;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateShortLinkDto {
    @NotNull
    @Size(min=4,max=1024)
    private String link;
}
