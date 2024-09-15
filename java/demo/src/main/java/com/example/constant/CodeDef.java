package com.example.constant;

/**
 * code定义
 * @author wenbin
 */
public interface CodeDef {
    /**
     * 成功返回code定义
     */
    interface Ok {
        String CODE = "0";
        String MASSAGE ="成功";
    }

    /**
     * 短地址校验失败返回code定义
     */
    interface ErrorCode001 {
        String CODE = "001";
        String MASSAGE ="请输入正确的短地址";
    }

    /**
     * 长地址校验失败返回code定义
     */
    interface ErrCode002 {
        String CODE = "002";
        String MASSAGE ="请输入正确的长地址";
    }

    /**
     * 获取长地址失败返回code定义
     */
    interface ErrCode003 {
        String CODE = "003";
        String MASSAGE ="获取长地址失败";
    }

    /**
     * 失败返回code定义
     */
    interface ErrCode999 {
        String CODE = "999";
        String MASSAGE ="操作失败";
    }
}
