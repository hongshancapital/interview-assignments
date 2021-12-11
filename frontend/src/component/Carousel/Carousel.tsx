import React, { HTMLAttributes, useRef, useCallback } from 'react';
import { UnionOmit } from 'src/util';
import { ICarouselOptions } from './model/interface';
import { CarouselWrapper } from './CarouselWrapper';


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
      children,
      onActiveChange,
      ...extraProps
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
      <CarouselWrapper
        ref={compRef}
        onActiveChange={onActiveChange}
        extra={extraProps}
      >
        {children}
      </CarouselWrapper>
    );
  },
);

export { Carousel };
