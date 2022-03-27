import React, { useCallback } from 'react';
import classnames from 'classnames';
import { data } from '../../../mock/data';
import { CarouselItemProps } from '../CarouselItem';
import style from './index.module.scss';

export interface DotsProps {
  data: CarouselItemProps[];
  interval: number;
  count: number;
  current: number;
  setCurrent: (id: number) => void;
}

export function Dots(props: DotsProps) {
  const { current, interval, setCurrent } = props;

  const getDuration = useCallback(
    (id: number) => `${id === current ? interval : 0}ms linear`,
    [current, interval]
  );

  const getClass = useCallback(
    (id: number) =>
      classnames(style.progress, {
        [style.active]: id === current,
      }),
    [current]
  );

  return (
    <ul className={style['my-carousel-dots']}>
      {data.map((item) => (
        <li
          key={item.id}
          className={style.dot}
          onClick={() => setCurrent(item.id)}
        >
          <span
            className={getClass(item.id)}
            style={{ transition: getDuration(item.id) }}
          ></span>
        </li>
      ))}
    </ul>
  );
}
