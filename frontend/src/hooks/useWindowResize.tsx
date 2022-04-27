/*
 * 监听窗口变化
 */

import { useState, useEffect } from 'react';

interface WindowAttr {
  width: number;
}

function useWindowResize(): WindowAttr {
  const [width, setWidth] = useState<number>(window.innerWidth);

  useEffect(() => {
    function listener(): void {
      // todo: 添加防抖
      setWidth(window.innerWidth);
      console.log('useWindowResize', window.innerWidth);
    }
    listener();
    window.addEventListener('resize', listener);
    return () => window.removeEventListener('resize', listener);
  }, []);

  return { width };
}

export default useWindowResize;