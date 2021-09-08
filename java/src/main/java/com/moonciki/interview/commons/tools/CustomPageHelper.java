package com.moonciki.interview.commons.tools;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.moonciki.interview.commons.model.PageData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("自动分页类")
public class CustomPageHelper<T> implements Serializable {
    public static final int default_page_index = 0;
    public static final int default_page_size = 0;

    @ApiModelProperty(value = "当前页码")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页显示页数")
    private Integer pageSize;

    @ApiModelProperty(value = "总行数")
    private Long recordCount;

    @ApiModelProperty(value = "总页数")
    private Integer pageCount;

    @ApiModelProperty(value = "排序")
    private String orderBy;

    @ApiModelProperty(value = "查询分页结果")
    private List<T> dataList;

    /** pagger **/
    @JSONField(serialize=false)
    @JsonIgnore
    private Page pagger;

    /**
     * 计算页数
     */
    public void initPageCount(){
        int totalPage = 0;

        if(pageSize != null && pageSize > 0){

            double countTmp = recordCount;
            double sizeTmp = pageSize;

            totalPage = (int) Math.ceil(countTmp / sizeTmp);

        }

        this.setPageCount(totalPage);
    }

    public CustomPageHelper() {
        initPagger(null, null);
    }

    /**
     * 适用于 自带的分页方式
     * @param pageNumber    当前页码
     * @param pageSize      页容量
     */
    public CustomPageHelper(Integer pageNumber, Integer pageSize){
        initPagger(pageNumber, pageSize);
    }

    /**
     * 初始化
     * @param pageNumber
     * @param pageSize
     */
    public void initPagger(Integer pageNumber, Integer pageSize){
        if(pageNumber == null){
            pageNumber = default_page_index;
        }
        if(pageSize == null){
            pageSize = default_page_size;
        }

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

    }

    /**
     * 查询之前 生成pagger
     * @return
     */
    public Page beforeQuery(){
        if (StringUtils.isNotBlank(orderBy) && pageSize <= 0) {
            PageHelper.orderBy(orderBy);
            pagger = PageHelper.getLocalPage();
        }else{
            pagger = PageHelper.startPage(pageNumber, pageSize, orderBy);
        }
        return pagger;
    }

    /**
     * 查询完毕，设置结果和页数
     */
    public void afterQuery(List<T> dataList){
        this.dataList = dataList;
        setRecordCount(pagger.getTotal());
        initPageCount();
    }

    /**
     * 返回分页数据
     * @return
     */
    public PageData<T> getPageData(){
        PageData<T> pageData = new PageData(pageNumber, pageSize, recordCount, pageCount, dataList);

        return pageData;
    }

}
