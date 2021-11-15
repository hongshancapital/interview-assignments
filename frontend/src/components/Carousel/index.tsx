import React, { FC, useState, useEffect, useRef, useCallback } from 'react';

import Dots from './dots';
import Slider from './slider';
import { useSlideData } from './hooks';
import defaultProps from './default-props';

import './index.css';

export interface CarouselProps {
  total: number;
  current: number;
}
export interface IProps {
  // 播放速度 s
  speed?: number;
  // 是否展示指示器
  dots?: boolean;
  // 起始页
  initPage?: number;
}

const Carousel: FC<IProps> = (props) => {
  const config = { ...defaultProps, ...props };
  const { speed, dots, initPage, children } = config;

  const { slideData, total } = useSlideData(children);
  // 当前页
  const [current, setCurrent] = useState(initPage);

  const handleCurrent = useCallback((index: number) => {
    setCurrent(index);
  }, []);

  const ref = useRef<NodeJS.Timeout>();
  useEffect(() => {
    const timer = setInterval(() => {
      setCurrent((current) => current + 1);
    }, speed * 1000);
    ref.current = timer;

    return () => {
      if (ref.current) {
        clearInterval(ref.current);
      }
    };
  }, [speed]);

  return (
    <div className="carousel" data-testid="test-carousel">
      <Slider
        slideData={slideData}
        total={total}
        current={current}
        handleCurrent={handleCurrent}
      />
      {dots ? <Dots total={total} current={current} speed={speed} /> : null}
    </div>
  );
};

export default Carousel;
