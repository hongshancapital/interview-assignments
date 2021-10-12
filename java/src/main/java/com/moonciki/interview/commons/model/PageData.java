package com.moonciki.interview.commons.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("分页统一返回类")
public class PageData<T> {

    @ApiModelProperty(value = "当前页码")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页显示页数")
    private Integer pageSize;

    @ApiModelProperty(value = "总行数")
    private Long recordCount;

    @ApiModelProperty(value = "总页数")
    private Integer pageCount;

    @ApiModelProperty(value = "查询分页结果")
    private List<T> dataList;

    public PageData() {
    }

    public PageData(Integer pageNumber, Integer pageSize, Long recordCount, Integer pageCount, List<T> dataList) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        this.pageCount = pageCount;
        this.dataList = dataList;
    }
}
