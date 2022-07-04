/*
 * @Author: danjp
 * @Date: 2022/6/23 16:17
 * @LastEditTime: 2022/6/23 16:17
 * @LastEditors: danjp
 * @Description:
 */
import { useState } from 'react';

export type SlideParams = {
  count: number;
  initialIndex?: number;
}

const useSlide = (options: SlideParams) => {
  const {
    count,
    initialIndex,
  } = options;
  
  const [current, setCurrent] = useState(() => {
    if (!initialIndex) return 0;
    if (initialIndex > count) return count - 1;
    if (initialIndex < 0) return 0;
    return initialIndex;
  });
  
  const slideGoTo  = (index: number) => {
    if (count <= 1) return;
    
    setCurrent(prevCurrent => {
      let nextCurrent = prevCurrent;
      if (index < 0) {
        nextCurrent = prevCurrent < 0 ? count - 1 : prevCurrent + index;
      } else if (index > 0) {
        nextCurrent = prevCurrent >= count - 1 ? 0 : prevCurrent + index;
      }
      return nextCurrent;
    });
  };
  
  return {
    current,
    slideGoTo,
  };
};

export default useSlide;
