import React from 'react';
import { useSingleton } from '../../../hooks';
import { Carousel, ICarouselOptions } from '../lib/Carousel';
import { CarouselContext } from '../RcCarousel/carouselContext';

export type IRcCarouselProps = ICarouselOptions & {
  children?: React.ReactNode;
};

export function RcCarousel(props: IRcCarouselProps): JSX.Element {
  const { children, ...options } = props;
  const carousel = useSingleton(() => new Carousel(options));
  carousel.updateCarouselOptions(options);

  return (
    <CarouselContext.Provider value={carousel}>
      {children}
    </CarouselContext.Provider>
  );
}
