import React, { HTMLAttributes, useRef, useCallback } from 'react';
import { RcCarousel } from './RcCarousel'
import { CarouselWrapper } from './CarouselWrapper';
import { ICarouselOptions } from './lib/Carousel';
import { UnionOmit } from './interface';

export type ICarousel = Partial<ICarouselOptions> & {
  onActiveChange?: (currentIndex: number) => void;
  children?: React.ReactNode;
};

export type ICarouselProps = UnionOmit<
  ICarousel,
  HTMLAttributes<HTMLDivElement>
>;
export interface CarouselRefType {
  goTo: (slide: number) => void;
  prev: () => void;
  next: () => void;
}

const Carousel = React.forwardRef<CarouselRefType, ICarouselProps>(
  (props, ref) => {
    const {
      delay = 400,
      speed = 3000,
      autoplay = false,
      paginationColor = '#989999',
      paginationPosition = 'center',
      paginationActiveColor = '#fff',
      onActiveChange,
      children,
      ...extra
    } = props;

    const compRef = useRef<CarouselRefType | null>(null);

    const goTo = useCallback((nextIndex: number): void => {
      compRef.current?.goTo(nextIndex);
    }, []);

    const prev = useCallback((): void => {
      compRef.current?.prev();
    }, []);

    const next = useCallback((): void => {
      compRef.current?.next();
    }, []);

    React.useImperativeHandle(ref, () => ({
      goTo,
      prev,
      next,
    }));

    return (
      <RcCarousel
        delay={delay}
        speed={speed}
        autoplay={autoplay}
        paginationColor={paginationColor}
        paginationPosition={paginationPosition}
        paginationActiveColor={paginationActiveColor}
      >
        <CarouselWrapper
          {...extra}
          ref={compRef}
          onActiveChange={onActiveChange}
        >
          {children}
        </CarouselWrapper>
      </RcCarousel>
    );
  },
);

export { Carousel };
