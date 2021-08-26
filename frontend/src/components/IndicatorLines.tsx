import React, { useCallback } from 'react';
import '../styles/IndicatorLines.css';

type Props = {
  itemsCount: number;
  activeIndex: number;
  onAnimationEnd: () => void;
};

function IndicatorLines({ itemsCount, activeIndex, onAnimationEnd }:Props) {
  const isActive = useCallback((index) => index === activeIndex, [activeIndex]);

  return (
    <div className="indicator-container" data-testid="indicator">
      {[...Array(itemsCount)].map((_, index) => index).map((currentIndex) => (
        <div className="indicator-item" key={currentIndex}>
          <div
            className={
            ['indicator-mask',
              isActive(currentIndex) ? 'indicator-mask-active' : ''].join(' ')
          }
            onAnimationEnd={onAnimationEnd}
            data-testid={`indicator-item-${currentIndex}`}
          />
        </div>
      ))}
    </div>
  );
}

export default IndicatorLines;
