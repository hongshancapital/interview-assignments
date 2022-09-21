import React, { CSSProperties, FC, ReactNode, useCallback, useEffect, useRef, useState } from 'react';
import css from './index.module.scss';
import Indicator from './Indicator';

interface ICarousel {
  style?: CSSProperties;
  periodicTime?: number;
  items?: Array<ReactNode>;
};

const Carousel: FC<ICarousel> = ({
  style,
  periodicTime = 3000,
  items = []
}) => {
  const [cur, setCur] = useState<number>(0);

  const timer = useRef<ReturnType<typeof setInterval>>();

  const autoPlay = useCallback(() => {
    if (timer.current) clearInterval(timer.current);
    timer.current = setInterval(() => {
      setCur(cur => (cur + 1) % items.length);
    }, periodicTime);
    return () => timer.current && clearInterval(timer.current);
  }, [items.length, periodicTime]);

  useEffect(autoPlay, [autoPlay]);

  const handleItemChange = (index: number) => {
    setCur(index % items.length);
    autoPlay();
  };

  return (
    <div className={css.wrapper} style={style}>
      <div
        className={css.innerWrapper}
        style={{
          transform: `translateX(-${cur * 100}%)`,
        }}
        data-testid="carousel-inner-wrapper"
      >
        {items.map((item, idx) => (
          <div
            key={idx}
            className={css.carouselItem}
          >
            {item}
          </div>
        ))}
      </div>
      <Indicator
        count={items.length}
        current={cur}
        setCur={handleItemChange}
        duration={periodicTime}
      />
    </div>
  )
};

export default Carousel;