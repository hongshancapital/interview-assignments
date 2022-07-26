import React from 'react';
import { useCarouselActiveIndex } from './useCarouselActiveIndex';

interface ICarouselProps {
  children: JSX.Element | JSX.Element[];
  slideDurationMs: number;
}

export function Carousel(props: ICarouselProps): JSX.Element {
  const children = Array.isArray(props.children) ? props.children : [props.children];
  if (children.length === 0) {
    return <></>;
  }

  const activeIndex = useCarouselActiveIndex(children.length, props.slideDurationMs);
  return (
    <div className='carouselContainer'>
      <div className='carouselSlidesWrapper'>
        {
          children.map((child, index) => (
            <div key={index} data-testid={`carousel-slide-${index}`}
                 className={`carouselSlide ${index === activeIndex ? 'carouselSlide__active' : ''}`}
            >
              {child}
            </div>
          ))
        }
      </div>
    </div>
  );
}