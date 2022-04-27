/*
 * 用法与 setInterval 相同
 * 解决 activeIndex 不更新的问题
 * 参考：https://zhuanlan.zhihu.com/p/455568085
 */

import React, { useEffect, useRef } from 'react';

function useInterval(callback: Function, delay: number, index: number): void {
  const savedCallback: React.MutableRefObject<any> = useRef();

  useEffect(() => {
    savedCallback.current = callback;
  });

  useEffect(() => {
    console.log('useInterval');
    function tick() {
      savedCallback.current();
    }
    let id = setInterval(tick, delay);
    return () => clearInterval(id);
  }, [delay, index]);
}

export default useInterval;