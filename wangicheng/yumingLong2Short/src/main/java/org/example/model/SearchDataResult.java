package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchDataResult {
    private List<Map<String, Object>> datas = new ArrayList<>(); // 返回数据
    private long total = 0; // 返回数据总数
    private int count = 0;

    public SearchDataResult() {
        super();
    }

    public List<Map<String, Object>> getDatas() {
        return datas;
    }

    public void setDatas(List<Map<String, Object>> datas) {
        this.datas = datas;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
