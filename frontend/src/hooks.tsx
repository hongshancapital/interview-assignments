/*
 * @Author: DJscript
 * @Date: 2023-03-31 15:34:27
 * @LastEditTime: 2023-03-31 15:34:28
 * @FilePath: /frontend/src/hooks.tsx
 * @Description: 自定义hook
 */
import React from 'react';

export const useInterval = (callback: () => void, delay: number) => {
  const savedCallback = React.useRef<() => void>();

  React.useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  React.useEffect(() => {
    function tick() {
      savedCallback.current && savedCallback.current();
    }
    if (delay !== null) {
      let id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
};
