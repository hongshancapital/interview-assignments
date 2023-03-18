import React, { memo, useCallback, useEffect, useState } from 'react';
import './index.scss';
import { CarouselProps, Slider } from './types';
/**
 * @description: Carousel Component
 * @param {CarouselProps}
 * @return {*}
 */
function Carousel({ sliders, startIndex, interval }: CarouselProps) {
  // Carousel active page index
  const [currentIndex, setCurrentIndex] = useState<number>(startIndex);
  // screen width
  const [winWidth, setwinWidth] = useState<number>(window.innerWidth);

  // resize hook
  useEffect(() => {
    const onResize = () => {
      setwinWidth((): number => {
        return window.innerWidth;
      });
    };

    window.addEventListener('resize', onResize);
    return () => {
      window.removeEventListener('resize', onResize);
    };
  }, []);

  /**
   * @description: indicator click function
   * @return {*}
   */
  const handleClick = useCallback(
    (targetIndex: number | string) => {
      console.log(targetIndex);
      if (typeof targetIndex === 'string' && targetIndex === 'auto') {
        return setCurrentIndex((currentIndex + 1) % sliders.length);
      }
      if (typeof targetIndex === 'number') {
        return setCurrentIndex(targetIndex % sliders.length);
      }
    },
    [currentIndex, sliders.length]
  );

  return (
    <div className="carousel">
      <div
        className="carousel-container"
        style={{
          width: `${winWidth * sliders.length}px`,
          transform: `translate(-${currentIndex * winWidth}px, 0)`,
        }}
      >
        {sliders.length > 0 &&
          sliders.map((item: Slider) => (
            <div
              className="carousel-item"
              key={item.id}
              style={{
                backgroundImage: `url(${item.image})`,
              }}
            >
              {item.title && (
                <h1 style={{ color: item.fontColor }}>{item.title}</h1>
              )}
              {item.subTitle && (
                <p style={{ color: item.fontColor }}>{item.subTitle}</p>
              )}
            </div>
          ))}
      </div>
      {sliders.length > 0 && (
        <ul className="carousel-indicator">
          {sliders.map((item: Slider, i: number) => (
            <li
              onAnimationStart={() => {
                handleClick(currentIndex);
              }}
              onAnimationEnd={() => {
                handleClick('auto');
              }}
              key={item.id}
              className={`${'carousel-indicator-item'} ${
                currentIndex === i ? 'active' : ''
              }`}
              style={
                {
                  '--animationDuration': `${interval / 1000}s`,
                } as React.CSSProperties
              }
              onClick={() => {
                handleClick(i);
              }}
            ></li>
          ))}
        </ul>
      )}
    </div>
  );
}
Carousel.defaultProps = {
  interval: 3000,
  startIndex: 0,
};

export default memo(Carousel);
