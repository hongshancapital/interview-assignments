package cn.office.luxixi.urlservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一包装类s
 *
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public static <T> Result ok(String message, T data) {
        return Result.<T>builder().code(200).message(message).data(data).build();
    }

    public static <T> Result error(String message, T data) {
        return Result.<T>builder().code(500).message(message).data(data).build();
    }
}
