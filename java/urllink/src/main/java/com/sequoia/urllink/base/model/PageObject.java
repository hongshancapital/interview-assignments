package com.sequoia.urllink.base.model;


import java.util.Collections;
import java.util.List;

/**
 * 分页信息:分页查找直接作为返回结果对象
 * @author liuhai
 * @date 2022.4.15
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PageObject<T extends AbstractPojo> {
  //分页查询【 selectPage$count默认数量查询】
  private   String selectPageName = ".selectPage";
  private  String selectSumName = ".selectSum";

  // 记录总数
  private long count;
  // 是否统计记录条数
  private Boolean countRecords = true;
  // 返回的结果集
  private List<T> data = Collections.EMPTY_LIST;
  //是否需要改变状态：导出excel和查询报表行为区分
  private boolean notChangeState;
  // 特定的查询条件
  private T param;
  //是否合计
  private boolean querySum;
  //合计
  private Object sum = Collections.EMPTY_MAP;
  // 总页数
  private long total;

  public PageObject() {
    super();
  }

  public String getSelectPageName() {
    return selectPageName;
  }

  public void setSelectPageName(String selectPageName) {
    this.selectPageName = String.format(".%s", selectPageName);
  }

  public String getSelectSumName() {
    return selectSumName;
  }

  public void setSelectSumName(String selectSumName) {
    this.selectSumName = String.format(".%s", selectSumName);
  }

  public static <T> ResultMessage<List<T>> newSuccessMessage(String message, List<T> data) {
    ResultMessage msg = new ResultMessage(ResultMessage.ResultCode.SUCCESS.getCode(), message, data);
    return msg;
  }

  public ResultMessage<List<T>> createSuccessMessage(String message) {
    ResultMessage<List<T>> resultMessage =
        new ResultMessage<>(ResultMessage.ResultCode.SUCCESS.getCode(), message, this.data, this.count, this.total);
    return resultMessage;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public Boolean getCountRecords() {
    return countRecords;
  }

  public void setCountRecords(Boolean countRecords) {
    this.countRecords = countRecords;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public T getParam() {
    return param;
  }

  public void setParam(T param) {
    this.param = param;
  }

  public Object getSum() {
    return sum;
  }

  public void setSum(Object sum) {
    this.sum = sum;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public boolean isNotChangeState() {
    return notChangeState;
  }

  public void setNotChangeState(boolean notChangeState) {
    this.notChangeState = notChangeState;
  }

  public boolean isQuerySum() {
    return querySum;
  }

  public void setQuerySum(boolean querySum) {
    this.querySum = querySum;
  }
}
