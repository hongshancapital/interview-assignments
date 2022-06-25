/*
 * @Author: danjp
 * @Date: 2022/6/23 16:53
 * @LastEditTime: 2022/6/23 16:53
 * @LastEditors: danjp
 * @Description:
 */
import React, { useEffect, useRef, useState } from 'react';

const useRect = (deps: React.DependencyList) => {
  const rootRef = useRef<HTMLDivElement | null>(null);
  const [size, setSize] = useState({ width: 0, height: 0 });
  
  const handleChangeSize = () => {
    const rect = rootRef.current?.getBoundingClientRect();
    if (!rect) return;
    
    setSize({
      width: rect.width,
      height: rect.height,
    });
  };
  
  useEffect(handleChangeSize, deps);
  
  return {
    rootRef,
    itemWidth: size.width,
    // 可以用来实现垂直方向的轮播
    itemHeight: size.height,
    onChangeSize: handleChangeSize,
  };
};

export default useRect;
