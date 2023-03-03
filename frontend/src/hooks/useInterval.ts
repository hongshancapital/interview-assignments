import { useEffect, useRef, useState } from "react";

/**
 * @description: 带暂停的周期定时器
 * @param {function} fn 执行的回调函数
 * @param {number} delay 周期时间
 * @param {object} options pause 是否处于暂停状态
 * @return {function} reset 重置剩余时间
 */
function useInterval(
  fn: () => void,
  delay: number,
  options?: {
    pause?: boolean;
  }
) {
  const fnRef = useRef(fn);
  fnRef.current = fn;
  const passTime = useRef(0);
  const startDate = useRef(Date.now());
  const [_, setState] = useState({});

  useEffect(() => {
    if (options?.pause) {
      passTime.current = Date.now() - startDate.current + passTime.current;
      return;
    }

    const timer = setInterval(() => {
      fnRef.current();
      passTime.current = 0;
    }, delay - passTime.current);
    startDate.current = Date.now();

    return () => {
      clearInterval(timer);
    };
  }, [delay, options, _]);

  const reset = () => {
    //暂停状态下重置剩余时间
    passTime.current = 0;
    startDate.current = Date.now();
    //非暂停状态下触发effect
    setState({});
  };

  return reset;
}

export default useInterval;
