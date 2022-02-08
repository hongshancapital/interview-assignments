import React, { FC, useState, useEffect, useCallback, useMemo } from "react";
import IndicatorItem from "../IndicatorItem";
import './index.scss';

export interface CarouselProps {
  duration?: number; // 停留时间 ms
  slideSpeed?: number; // 滑动时间 ms
  slideItems: React.ReactElement[];
}

let timer: NodeJS.Timeout | null = null;

const Carousel: FC<CarouselProps> = ({
  duration = 5000,
  slideSpeed = 200,
  slideItems,
}) => {
  const total = slideItems.length;
  const [currIndex, setCurrIndex] = useState(0);

  const clear = useCallback(() => {
    timer && clearInterval(timer);
  }, [])

  const autoPlay = useCallback(() => {
    timer = setInterval(() => {
      setCurrIndex((prev) => (prev + 1) % total);
    }, duration);
  }, [total, duration]);

  useEffect(() => {
    autoPlay();
    return () => {
      clear();
    };
  }, [autoPlay, clear]);

  const handleClk = useCallback(
    (index: number) => {
      setCurrIndex(index);
      clear();
      autoPlay();
    },
    [autoPlay, clear],
  );

  const transformStyle = useMemo(() => {
    return ({
      width: `${total * 100}%`,
      transitionDuration: `${slideSpeed}ms`,
      transform: `translateX(${(-currIndex / total) * 100}%)`,
    });
  }, [total, slideSpeed, currIndex]);

  return (
    <div className="carouse-wrapper">
      <div className="loop-container" style={transformStyle}>
        {slideItems.map((item, index) => {
          return <div className="loop-item" key={index} style={{ width: (1 / total) * 100 + '%' }}> {item}</div>;
        })}
      </div>
      <div className="indicator">
        {slideItems.map((item, index) => {
          return <IndicatorItem key={index} active={index === currIndex} duration={duration} onClick={() => handleClk(index)} />
        })}
      </div>
    </div >
  );
};

export default Carousel;
