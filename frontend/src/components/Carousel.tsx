/** @format */

import React, { useState, useRef, useEffect, Children, ReactElement } from 'react';
import './Carousel.css';

type CarouselProps = {
  children: React.ReactNode[];
  delay?: number;
  speed?: number;
};

const Carousel: React.FC<CarouselProps> = ({ children, delay = 2000, speed = 300 }) => {
  const childrenLength = Children.count(children);

  const container = useRef<HTMLDivElement>(null);
  const [clientWidth, setClientWidth] = useState(0);
  const [activeIndex, setActiveIndex] = useState(0);
  const [translateX, setTranslateX] = useState(0);

  useEffect(() => {
    setClientWidth(container.current?.clientWidth ?? 0);
    const resizeObserver = new ResizeObserver((entries) => {
      setClientWidth(container.current?.clientWidth ?? 0);
    });
    container.current && resizeObserver.observe(container.current);

    return () => {
      resizeObserver.disconnect();
    };
  }, [container]);

  useEffect(() => {
    setTranslateX(-activeIndex * clientWidth);
  }, [clientWidth, activeIndex]);

  useEffect(() => {
    const timer = setTimeout(() => {
      updateActiveIndex(activeIndex + 1);
    }, delay);

    return () => clearTimeout(timer);
  });

  const resetAnimation = () => {
    document.getAnimations().forEach((animation) => {
      animation.cancel();
      animation.play();
    });
  };

  const updateActiveIndex = (i: number) => {
    if (i >= childrenLength) {
      i = 0;
    } else if (i < 0) {
      i = childrenLength - 1;
    }
    setActiveIndex(i);
  };

  const onChangeIndex = (index: number) => {
    updateActiveIndex(index);
    resetAnimation();
  };

  return (
    <div className="Carousel" ref={container}>
      <div
        className="wrapper"
        data-testid="wrapper"
        style={{
          width: clientWidth * childrenLength + 'px',
          transform: `translate3d(${translateX}px, 0, 0)`,
          transition: `transform ${speed}ms ease-in-out`,
        }}
      >
        {Children.map(children, (child, idx) => {
          return React.cloneElement(child as ReactElement, {
            style: {
              width: clientWidth + 'px',
            },
          });
        })}
      </div>
      <div className="progress">
        {Children.map(children, (child, idx) => {
          return (
            <div className="bar-box" onClick={() => onChangeIndex(idx)}>
              <div
                className="bar"
                data-testid={'bar-' + idx}
                style={{
                  animationDuration: activeIndex === idx ? `${delay}ms` : '0s',
                  backgroundColor: activeIndex === idx ? '#fff' : 'unset',
                }}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
