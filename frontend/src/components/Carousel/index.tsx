import React, { CSSProperties, FC, ReactNode, useEffect, useState } from 'react';
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
  const [cur, setCur] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCur(cur => (cur + 1) % items.length);
    }, periodicTime);
    return () => clearInterval(timer);
  }, [periodicTime, items.length]);

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
        setCur={setCur}
        duration={periodicTime}
      />
    </div>
  )
};

export default Carousel;