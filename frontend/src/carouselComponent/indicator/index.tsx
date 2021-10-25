import React, { useCallback } from 'react';
import './style.css';

type Props = {
  itemsCount: number;
  activeIndex: number;
  onAnimationEnd: () => void;
};

export default function Indicator({ itemsCount, activeIndex, onAnimationEnd }:Props) {
  const isActive = useCallback((index) => index === activeIndex, [activeIndex]);

  return (
    <div className="indicator-container">
      {[...Array(itemsCount)].map((_, index) => index).map((currentIndex) => (
        <div className="indicator-item" key={currentIndex}>
          <div
            className={
            ['indicator-mask',
              isActive(currentIndex) ? 'indicator-mask-active' : ''].join(' ')
          }
            onAnimationEnd={onAnimationEnd}
          />
        </div>
      ))}
    </div>
  );
}
