package com.hongshang.shorturlmodel.api;



/**
 * 产生短8位字符串的策略模式
 */
public interface IGetShortStrStrategy {

     /**
      * 获取8位短地址
      *
      * @return
      */
     String  getNextShortStr();
}
