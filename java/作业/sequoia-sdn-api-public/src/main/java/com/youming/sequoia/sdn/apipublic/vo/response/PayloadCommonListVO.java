package com.youming.sequoia.sdn.apipublic.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel("数据载荷-通用列表数据")
@Data
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
public class PayloadCommonListVO<T> {

    @ApiModelProperty(value = "记录总数")
    private long total = 0;

    @ApiModelProperty(value = "列表数据")
    private List<T> list = new ArrayList<T>();
}
