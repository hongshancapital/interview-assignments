/*
 * Interval Hook
 * 解决 Carousel 组件 activeIndex 不更新的问题
 * 参考：https://zhuanlan.zhihu.com/p/455568085
 */

import React, { useEffect, useRef } from 'react';

function useInterval(callback: Function, delay: number, index: number) {
  const savedCallback: React.MutableRefObject<any> = useRef();

  useEffect(() => {
    savedCallback.current = callback;
  });

  useEffect(() => {
    if (index < 0) return;  // index 传负值会阻止加载定时器
    function tick() {
      savedCallback.current();
    }
    let id = setInterval(tick, delay);
    return () => clearInterval(id);
  }, [delay, index]);
}

export default useInterval;
