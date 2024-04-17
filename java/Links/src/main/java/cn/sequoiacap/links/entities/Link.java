package cn.sequoiacap.links.entities;

import cn.sequoiacap.links.entities.groups.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Liushide
 * @date :2022/4/5 18:48
 * @description : 链接实体类
 */
@ApiModel
@Getter
@Setter
public class Link  implements Serializable {
    /**
     * 长链接
     */
    @ApiModelProperty(name="longLink", value="长链接", required=true, example ="https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo&user_name=lucas")
    @NotBlank(groups = {Insert.class}, message =" 不能为空")
    @Pattern(groups = {Insert.class}, regexp = "([hH][tT]{2}[pP][sS]?:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+(\\\\?{0,1}(([A-Za-z0-9-~]+\\\\=?)([A-Za-z0-9-~]*)\\\\&?)*)", message = " 格式不符合要求")
    private String longLink;

    /**
     * 短代码
     */
    @ApiModelProperty(name="shortCode", value="短代码", required=false, example = "ra6vAv")
    private String shortCode;

    /**
     * 短链接
     */
    @ApiModelProperty(name="shortLink", value="短链接", required=false, example = "https://sc.io/ra6vAv")
    private String shortLink;
}
