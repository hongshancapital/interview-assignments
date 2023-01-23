package org.zxl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Segment {
    //开始数字
    private String start;
    //结束数字
    private String end;

    public Segment(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
