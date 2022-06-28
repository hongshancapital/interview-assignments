/**
 * 常量
 */

//状态
export enum STATE {
    EFFECT = 'effect',
    INVALID = 'invalid'
}

// 62base 基础字符串
export const CHARSET: string = "~9876543210ABCDEFGHIJKLMNOPQRSTU$#@%!abcdefghijklmnopqrstuvw-=";

// 正则的效验
export const REGEXP = {
    HTTP : /(http|https):\/\/\S*/
}

//自增的step值
export const SEQ_STEP: number = 1;


