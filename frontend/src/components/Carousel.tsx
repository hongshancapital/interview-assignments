import React, { useEffect, useRef, useState, CSSProperties, FC } from "react";
import "./Carousel.css";

export interface CarouselProps {
  children?: React.ReactNode
  duration?: number             // 每帧页面停留的时长，单位是ms，默认2600 
  transitionDuration?: number   // 页面切换的耗时，单位是ms, 默认400
  style?: CSSProperties         // 给容器设定的样式，简单传递
  className?: string            // 给容器设置的样式名，会和容器元素本身的样式名carousel-container合并
}

const Carousel:FC<CarouselProps> = ({ duration = 2600, children, style, transitionDuration = 400, className }) => {
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const timer = useRef<null | number>(null);
  const len:number = React.Children.count(children);

  useEffect(() => {
    timer.current = window.setInterval(() => {
      setCurrentIndex(i => i + 1 >= len ? 0 : i + 1);
    }, duration);
    return () => {
      if (timer.current) {
        clearInterval(timer.current);
        timer.current = null;
      }
    }
  }, [duration, len]);

  const wrapperStyle: CSSProperties = {
    transitionDuration: transitionDuration + 'ms',
    transform: `translateX(-${currentIndex * 100}%)`
  };

  const indicatorActiveStyle: CSSProperties = {
    animationDuration: duration + 'ms',
  };
  
  return (
    <div className={`carousel-container${className ? ' ' + className : ''}`} style={style}>
      <div className="carousel-wrapper" style={wrapperStyle}>
        {children}
      </div>
      <div className="indicator">
        {React.Children.map(children, (child, i) => (
          <span
            className={`indicator-item ${i < currentIndex ? 'visited' : (i === currentIndex ? 'active' : 'default')}`}
            style={i === currentIndex ? indicatorActiveStyle : undefined}
          >
          </span>
        ))}
      </div>
    </div>
  );
};

export default Carousel;