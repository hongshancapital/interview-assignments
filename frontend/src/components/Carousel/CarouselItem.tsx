import React from 'react';
import { CarouselItemProps } from "./interface";
import { DEFAULT_IMG_HEIGHT } from './constants';

import './index.scss';

const CarouselItem: React.FC<CarouselItemProps> = (props: CarouselItemProps) => {
  const {
    src = '',
    alt = '',
    title = [],
    desc = [],
    imgHeight = DEFAULT_IMG_HEIGHT,
    wrapperStyle = {},
  } = props || {};

  return (
    <div className="carousel-item" style={wrapperStyle}>
      <div className="content">
        <div className="title">
          {title.map((c, idx) => (
            <div key={`carousel_title_${idx}`}>{c}</div>
          ))}
        </div>
        <div className="desc">
          {desc.map((c: string, idx: number) => (
            <div key={`carousel_desc_${idx}`}>{c}</div>
          ))}
        </div>
      </div>
      <img src={src} alt={alt} style={{ height: imgHeight }} />
    </div>
  );
};

export default CarouselItem;