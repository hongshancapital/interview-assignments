import React, { useEffect, useState } from 'react';
import classNames from 'classnames';
import './style.sass';

export interface CarouselProps {
  autoplay: boolean; // 是否自动播放
  duration: number; // 滚动时间
  showDot?: boolean; // 是否展示滚动提示条
}

interface CarouselDotProps {
  showDot: boolean;
  count: number;
  current: number;
  duration: number;
  dotClick: Function;
}

const CarouselDot: React.FC<CarouselDotProps> = ({
  showDot,
  count,
  current,
  duration,
  dotClick,
}) => {
  if (showDot && count) {
    const items = new Array(count).fill(0);
    return (
      <ul className="c-carousel-dot">
        {items.map((i, index) => {
          return (
            <li
              key={i + index}
              onClick={() => {
                dotClick(index);
              }}
              className={classNames('c-carousel-dot-item', {
                'c-carousel-dot-item-active': current === index,
              })}
              style={{ width: (1 / count) * 100 + '%' }}
            >
              <span style={{ animationDuration: duration + 'ms' }}></span>
            </li>
          );
        })}
      </ul>
    );
  } else {
    return <></>;
  }
};

const Carousel: React.FC<CarouselProps> = ({
  autoplay = true,
  duration = 4000,
  showDot = true,
  children,
}) => {
  const childrenCount = React.Children.count(children);
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  let timer: null | NodeJS.Timer = null;

  useEffect(() => {
    if (!autoplay) {
      return;
    }
    let timer: null | NodeJS.Timer = setInterval(() => {
      setCurrentIndex(currentIndex >= childrenCount - 1 ? 0 : currentIndex + 1);
    }, duration);

    return () => {
      if (timer) {
        clearInterval(timer);
        timer = null;
      }
    };
  }, [autoplay, childrenCount, currentIndex, duration]);

  const dotClick = (index: number) => {
    setCurrentIndex(index);
    if (timer) {
      clearInterval(timer);
      timer = null;
    }
  };

  return (
    <div className="c-carousel">
      <div
        className="c-carousel-wrapper"
        style={{
          width: childrenCount * 100 + '%',
          transform: `translateX(-${(currentIndex / childrenCount) * 100}%)`,
        }}
      >
        {children}
      </div>
      <CarouselDot
        showDot={showDot}
        current={currentIndex}
        count={childrenCount}
        duration={duration}
        dotClick={dotClick}
      />
    </div>
  );
};

export default Carousel;
