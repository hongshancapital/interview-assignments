package com.scdt.interview.assignments.bean.base;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity {
    private String code;
    private String message;
    private Object data;
}
