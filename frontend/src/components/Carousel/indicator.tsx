import React from 'react';
import './indicator.css';

/**
 * 轮播图-指示器属性接口
 */
export interface ICarouselIndicator {
  onClick?: () => void;
  isActive?: boolean;
  interval?: number;
  auto?: boolean;
}

/**
 * 轮播图-指示器
 */
export function CarouselIndicator({
  onClick,
  isActive,
  interval,
  auto,
}: ICarouselIndicator) {
  const onHandleClick = () => {
    if (onClick && typeof onClick === 'function') onClick();
  };

  return (
    <div className='carousel_indicator' onClick={onHandleClick}>
      <div
        className={`carousel_indicator__process ${
          isActive && auto ? 'carousel_indicator__active' : ''
        }`}
        style={{ animationDuration: `${interval}ms` }}
      ></div>
    </div>
  );
}
