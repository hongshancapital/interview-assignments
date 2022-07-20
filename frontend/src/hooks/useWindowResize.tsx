/*
 * 监听窗口变化，返回 window 属性
 */

import { useState, useEffect } from 'react';

export interface WindowAttr {
  width: number
  // ...其他 window 属性
};

function useWindowResize(): WindowAttr {
  const [width, setWidth] = useState(window.innerWidth);

  useEffect(() => {
    const listener = () => {
      // todo: 添加防抖
      setWidth(window.innerWidth);
    }
    listener();
    window.addEventListener('resize', listener);
    return () => window.removeEventListener('resize', listener);
  }, []);

  return { width };
}

export default useWindowResize;
