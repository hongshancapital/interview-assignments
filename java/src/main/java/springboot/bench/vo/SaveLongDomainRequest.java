package springboot.bench.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "springboot.bench.vo.SaveLongDomainRequest", description = "保存长链生成短链")
public class SaveLongDomainRequest {
    
    @ApiModelProperty(value = "长链")
    private String longDomain;

    public SaveLongDomainRequest() {
        super();
    }
    
    public SaveLongDomainRequest(String longPath) {
        super();
        this.longDomain = longPath;
    }

    public String getLongDomain() {
        return longDomain;
    }

}
