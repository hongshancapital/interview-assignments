/*
 * @Description: 定时器hooks
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-09 23:16:55
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-12 23:07:24
 */

import { useEffect, useRef } from "react";
/**
 * @description:循环执行hooks
 * @param fn 回调函数
 * @param delay 执行间隔时间(秒)
 * @param immediate 是否立即执行(默认false)
 * @return {void}
 * @author: zhangw
 */
const useInterval = (fn: () => void, delay: number = 1, immediate = false) => {
  const fnRef = useRef(fn);
  useEffect(() => {
    immediate && fnRef.current && fnRef.current();
    const interval = setInterval(() => {
      fnRef.current && fnRef.current();
    }, delay * 1000);
    return () => {
      clearInterval(interval);
    };
  }, [delay,immediate]);
};

export default useInterval;
