package com.moonciki.interview.commons.model;

import com.moonciki.interview.commons.enums.ResponseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 返回码类
 */
@Data
public class ResponseCode {

    /** 返回码 **/
    private String codeName;
    /** 描述信息 **/
    private String dec;

    public ResponseCode() {
    }

    public ResponseCode(String codeName, String dec) {
        this.codeName = codeName;
        this.dec = dec;
    }

    /**
     * 默认code
     * @param codeObj
     * @return
     */
    public static ResponseCode fixCode(ResponseCode codeObj){

        if(codeObj == null) {
            codeObj = ResponseEnum.sys_error.info();
        }

        String codeName = codeObj.getCodeName();
        String dec = codeObj.getDec();

        if(StringUtils.isBlank(codeName)) {
            codeName = ResponseEnum.sys_error.name();

            codeObj.setCodeName(codeName);
        }

        if(StringUtils.isBlank(dec)) {
            dec = ResponseEnum.sys_error.getDec();

            codeObj.setDec(dec);
        }

        return codeObj;
    }
}
