<?php


namespace app\libs;


class ApiCodeConst
{
    // 接口返回状态码
    const CODE_STATUS_OK = '0';
    const CODE_STATUS_VALIDATA_ERR = '1';
    const CODE_STATUS_DATA_ERR = '101';
    const CODE_STATUS_FAIL = '500';
    const CODE_STATUS_SETNX_LOCK_FAIL = '600';

    //接口返回状态码 对应 msg
    public static  $codesInfo = [
        self::CODE_STATUS_OK => 'ok',
        self::CODE_STATUS_VALIDATA_ERR => '参数有误',
        self::CODE_STATUS_DATA_ERR => '验签错误，请稍后再试',
        self::CODE_STATUS_FAIL => '系统错误，请稍后再试',
        self::CODE_STATUS_SETNX_LOCK_FAIL => '正在请求中'
    ];
}