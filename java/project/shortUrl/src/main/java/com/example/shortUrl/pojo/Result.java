package com.example.shortUrl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author HOPE
 * @Description 响应类
 * @Date 2022/4/28 19:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    public int code;
    public String msg;
    public List<T> data;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

