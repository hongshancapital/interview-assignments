package springboot.bench.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "springboot.bench.vo.QueryShortDomainRequest", description = "短链请求pojo")
public class QueryShortDomainRequest {
    
    @ApiModelProperty(value = "短链")
    private String shortDomain;

    public QueryShortDomainRequest() {
        super();
    }

    public QueryShortDomainRequest(String shortDomain) {
        super();
        this.shortDomain = shortDomain;
    }

    public String getShortDomain() {
        return shortDomain;
    }

    public void setShortDomain(String shortDomain) {
        this.shortDomain = shortDomain;
    }

}
