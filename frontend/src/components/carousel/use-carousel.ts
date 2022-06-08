import { useEffect, useState } from 'react';

import { useEvent, useInterval } from '../../hooks';

import type { CSSProperties } from "react";

const animationTime = 300;

export const useCarousel = (count: number, _interval: number | null) => {
  const [interval, changeInterval] = useState(_interval);
  const [current, setCurrent] = useState(0);

  // 响应组件 props interval 变化
  useEffect(() => {
    changeInterval(_interval);
  }, [_interval]);

  // clone 元素数量需要计算在内
  const total = count + 1;

  // autoPlay, interval 为 null 取消定时器
  useInterval(() => {
    const next = current + 1;
    setCurrent(next);
    if (next + 1 === total) {
      // 无缝轮播- current 复位
      setTimeout(() => {
        setCurrent(0);
      }, animationTime);
    }
  }, interval);

  // 1. 固定函数引用，避免造成业务方 memo 优化失效
  // 2. 也可直接使用 useCallback，play 函数注意维护 dependencyList 即可
  const pause = useEvent(() => {
    changeInterval(null);
  });

  const play = useEvent(() => {
    changeInterval(_interval);
  });

  const trackStyle: CSSProperties = {
    width: `${total * 100}%`,
    transform: `translateX(-${100 * (current / total)}%)`,
    // 无缝轮播-无动效复位
    transition: current === 0 ? "none" : `transform ${animationTime}ms ease`,
  };

  const slideStyle: CSSProperties = { width: `${100 / total}%` };

  return {
    carouselGoTo: setCurrent,
    play,
    pause,
    current: current + 1 === total ? 0 : current,
    trackStyle,
    slideStyle,
  };
};
