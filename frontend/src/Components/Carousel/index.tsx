import React, { FC, useEffect, useRef, useState, useCallback } from 'react';
import ProgressBar from '../ProgressBar';
import './index.css';

const MIN_WIDTH = 0;

const Carousel: FC<{ duration?: number }> = ({ children, duration = 1000 }) => {
  const timer = useRef<NodeJS.Timeout>();
  const [activeIndex, setActiveIndex] = useState(0);
  const [carouselWidth, setCarouselWidth] = useState(MIN_WIDTH);
  const carouselRef = useRef<HTMLDivElement>(null);

  const childrenCount = React.Children.count(children);

  const childrenArr = React.Children.map(children, (child, index) => {
    if (!React.isValidElement(child)) {
      return child;
    }

    const propsConfig = {
      styles: {
        ...child.props.styles,
        width: `${carouselWidth}px`,
        display: 'inline-block',
      },
      key: index,
    };

    return React.cloneElement(child, propsConfig);
  });

  useEffect(() => {
    setCarouselWidth(carouselRef.current?.clientWidth || MIN_WIDTH);

    timer.current = setInterval(() => {
      if (activeIndex === childrenCount - 1) {
        setActiveIndex(0);
        return;
      }

      setActiveIndex(activeIndex + 1);
    }, duration);

    return () => {
      clearInterval(timer.current!);
    };
  }, [duration, childrenCount, activeIndex]);

  const onProgressItemClick = useCallback<
    React.MouseEventHandler<HTMLDivElement>
  >(e => {
    const {
      currentTarget: { dataset },
    } = e;

    setActiveIndex(+(dataset?.index || 0));
  }, []);

  return (
    <div className="carousel" ref={carouselRef}>
      {carouselWidth && (
        <div
          className="carousel-body"
          style={{
            width: `${childrenCount * carouselWidth}px`,
            transform: `translateX(${-activeIndex * carouselWidth}px)`,
          }}
        >
          {childrenArr}
        </div>
      )}
      <ProgressBar
        count={childrenCount}
        duration={duration}
        activeIndex={activeIndex}
        onProgressItemClick={onProgressItemClick}
      />
    </div>
  );
};

export default Carousel;
