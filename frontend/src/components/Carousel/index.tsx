
import React from 'react';
import CarouselItem from './carouselItem';
import { useActiveIndex } from './hooks';
import Progress from './progress';

import './index.scss';

export interface ICarouselItem {
  src: string;
  title: string[];
  desc?: string[];
  alt?: string;
  color?: string;
}


interface Props {
  items: Array<ICarouselItem>,
  time?: number;
}

const defaultTime = 3000;

export default function Carousel(props: Props) {
 
  const { items, time = defaultTime } = props;

  const activeIndex = useActiveIndex({ time, count: items.length });

  return (
    <div className="carousel">
      <div className="carousel-items" style={{ transform: `translateX(-${activeIndex * 100}%)`}}>
        {items.map(item => {
          return <CarouselItem {...item} ></CarouselItem>;
        })}
      </div>
      <Progress count={items.length} time={time} activeIndex={activeIndex} ></Progress>
    </div>
  );
}