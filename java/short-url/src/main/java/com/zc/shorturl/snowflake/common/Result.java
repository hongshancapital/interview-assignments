package com.zc.shorturl.snowflake.common;

import lombok.*;

/**
 * @description  Result
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private long id;

    private String message;

    private IdStatus idStatus;

    //系统回拨
    public static Result systemClockGoBack() {
        return new Result(-1,"Current server system clock go back !", IdStatus.EXCEPTION);
    }

    //正常
    public static Result ok(long id) {
        return new Result(id,null, IdStatus.SUCCESS);
    }
}
