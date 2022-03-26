import React, { useMemo } from 'react';
import classNames from 'classnames';
import style from './index.module.scss';

export interface CarouselItemData {
  id: number;
  img: string;
  title: string;
  bgColor: string;
  color?: string;
  desc?: string;
}

export interface CarouselItemProps extends CarouselItemData {
  current: number;
}

export function CarouselItem(props: CarouselItemProps) {
  const { id, current, img, title, desc, bgColor, color = '#000000' } = props;

  const className = useMemo(
    () =>
      classNames(style['my-carousel-item'], {
        [style.active]: id === current,
      }),
    [current, id]
  );

  return (
    <div className={className} style={{ background: bgColor, color }}>
      {!!title && <h3 className={style.title}>{title}</h3>}
      {!!desc && <h5 className={style.desc}>{desc}</h5>}

      <img className={style.image} src={img} alt={desc || title} />
    </div>
  );
}
