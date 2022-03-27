import React from 'react';
import style from './index.module.scss';

export interface CarouselItemProps {
  id: number;
  img: string;
  title: string;
  bgColor: string;
  color?: string;
  desc?: string;
}

export function CarouselItem(props: CarouselItemProps) {
  const { img, title, desc, bgColor, color = '#000000' } = props;

  return (
    <div
      className={style['my-carousel-item']}
      style={{ background: bgColor, color }}
    >
      {!!title && <h3 className={style.title}>{title}</h3>}
      {!!desc && <h5 className={style.desc}>{desc}</h5>}

      <img className={style.image} src={img} alt={desc || title} />
    </div>
  );
}
