import { useEffect, useRef, useState } from 'react'

/**
 * useInterval 定时器
 * @param callback 回调函数
 * @param delay 延迟时间
 * @param switchOn 定时器开关
 */
function useInterval(callback: () => void, delay: number = 0, switchOn: boolean = true) {
  const savedCallback = useRef(callback);
  const [ canStart, toggleIntervalSwitch ] = useState(true);
  // 保存新回调
  useEffect(() => {
    savedCallback.current = callback;
  }, []);

  // 建立 interval
  useEffect(() => {
    function tick() {
      savedCallback.current();
    }

    if (switchOn && canStart && delay !== null) {
      let id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [canStart, delay, switchOn]);

  const resetInterval = () => {
    toggleIntervalSwitch(false);
    setTimeout(() => {
      toggleIntervalSwitch(true);
    });
  };

  return {
    resetInterval,
    toggleIntervalSwitch
  };
}

export default useInterval;