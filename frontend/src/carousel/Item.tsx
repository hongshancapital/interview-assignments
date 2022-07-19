import React from 'react';
import type { CarouselItemProps } from './types';

const Item = ({ className, titles, titleClassName, describes, describeClassName, img }: CarouselItemProps) => (
  <div className={className ? `carousel-item ${className}` : 'carousel-item'}>
    <div className="carousel-item-text">
      <div className={titleClassName}>
        {
          titles?.map((title, i) => (<div key={i}>{title}</div>))
        }
      </div>
      <div className={describeClassName}>
        {
          describes?.map((describe, i) => (<div key={i}>{describe}</div>))
        }
      </div>
    </div>
    { 
      img ? <img src={img} alt={titles?.[0]} /> : null 
    }
  </div>
);

export default Item;
