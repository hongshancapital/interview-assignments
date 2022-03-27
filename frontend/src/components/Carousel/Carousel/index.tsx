import React, { useEffect, useMemo, useState } from 'react';
import { CarouselItem, CarouselItemProps } from '../CarouselItem';
import { Dots } from '../Dots';
import style from './index.module.scss';

export interface CarouselProps {
  interval?: number;
  showDots?: boolean;
  data: CarouselItemProps[];
}

export function Carousel(props: CarouselProps) {
  const { showDots, data, interval = 5000 } = props;

  const [current, setCurrent] = useState<number>(-1);

  // 组件挂载之后再设置 current
  useEffect(() => {
    if (data.length) {
      setCurrent(1);
    }
  }, [data]);

  useEffect(() => {
    const timer = setTimeout(() => {
      const now = current >= data.length ? 1 : current + 1;
      setCurrent(now);
    }, interval);

    return () => clearTimeout(timer);
  }, [current, data, interval]);

  const width = useMemo(() => `${data.length * 100}%`, [data]);

  const left = useMemo(() => `-${(current - 1) * 100}%`, [current]);

  return (
    <div className={style['my-carousel']}>
      <div
        className={style.list}
        style={{ width, left, transition: `300ms linear` }}
      >
        {data.map((item) => (
          <CarouselItem {...item} key={item.id} />
        ))}
      </div>

      {showDots && data.length > 1 && (
        <Dots
          setCurrent={setCurrent}
          current={current}
          interval={interval}
          count={data.length}
          data={data}
        />
      )}
    </div>
  );
}
