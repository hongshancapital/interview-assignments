package com.moonciki.interview.commons.model;

import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import com.moonciki.interview.commons.tools.CustomPageHelper;
import com.moonciki.interview.utils.CommonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * 分页查询参数
 */
@Data
@ApiModel("分页参数")
public class PageParameter {

    /**
     * 表列名正则
     */
    public static final String reg_column_name = "^[a-zA-Z0-9_-]+$";

    @ApiModelProperty(value = "当前页码")
    private Integer pageNumber = 1;

    @ApiModelProperty(value = "每页显示页数")
    @Range(min = 1)
    private Integer pageSize = 10;

    @ApiModelProperty(value = "分页开始的位置（尽量使用 pageNumber）")
    private Integer start;

    @ApiModelProperty(value = "排序")
    private String orderBy;

    @ApiModelProperty("0正序，1倒序")
    private Integer sort;

    public void initPage(Integer pageNumber, Integer pageSize){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 计算开始条数
     */
    public void initStart(){

        if(pageSize != null && pageNumber != null){
            start = (pageNumber - 1) * pageSize;
        }

    }

    public String getOrderBy() {
        if(StringUtils.isNotBlank(orderBy)) {
            if (!CommonUtil.regMatch(PageParameter.reg_column_name, orderBy)) {
                throw CustomException.createException(ResponseEnum.illegal_req.info());
            }
        }
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        if(StringUtils.isNotBlank(orderBy)) {
            if (!CommonUtil.regMatch(PageParameter.reg_column_name, orderBy)) {
                throw CustomException.createException(ResponseEnum.illegal_req.info());
            }
        }

        this.orderBy = orderBy;
    }

    /**
     * 生成 PageHelper
     * @return
     */
    public CustomPageHelper createSqlPager(){
        CustomPageHelper pageHelper = new CustomPageHelper(pageNumber, pageSize);

        String fullOrderBy = null;
        if(StringUtils.isNotBlank(orderBy)){
            if(!CommonUtil.regMatch(PageParameter.reg_column_name, orderBy)){
                throw CustomException.createException(ResponseEnum.illegal_req.info());
            }
            fullOrderBy = orderBy;

            if(sort != null && sort == 1){
                fullOrderBy = fullOrderBy + " desc";
            }
        }

        pageHelper.setOrderBy(fullOrderBy);

        return pageHelper;
    }

    public PageData createPageData(Long recordCount, List<?> dataList){
        CustomPageHelper pageHelper = this.createSqlPager();

        pageHelper.setRecordCount(recordCount);
        pageHelper.setDataList(dataList);
        pageHelper.initPageCount();

        PageData pageData = pageHelper.getPageData();

        return pageData;
    }

}

