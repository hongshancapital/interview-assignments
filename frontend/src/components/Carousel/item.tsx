import React from 'react';
import './item.css';

export interface ICarouselItem {
  url?: string;
  title?: string;
  style?: {
    [k in string]: string;
  };
  onClick?: () => void;
}

/**
 * 轮播图-选项
 */
export function CarouselItem({
  url,
  title,
  style,
  onClick,
}: ICarouselItem = {}) {
  return (
    <div
      className='carousel_item'
      style={style}
      onClick={() => onClick && typeof onClick === 'function' && onClick()}
    >
      {url && (
        <img
          className='carousel_item__image'
          src={url}
          alt={title || url}
        ></img>
      )}
      {title && <div className='carousel_item__title'>{title}</div>}
    </div>
  );
}
