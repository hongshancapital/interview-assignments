import React from 'react';
import { ICarouselItem } from './index';

export default function CarouselItem(props: ICarouselItem) {

  const { title, desc, src, alt, color} = props;

  return (
    <div className='carousel-item'>
      <img className='carousel-item-image' src={src} alt={alt ?? ''} />
      <div className='carousel-item-wrapper' style={{ color }}>
        {title.map(t => <p className='carousel-item-title'>{t}</p>)}
        {desc?.map(d => <p className='carousel-item-desc'>{d}</p>)}
      </div>
    </div>
  )
} 