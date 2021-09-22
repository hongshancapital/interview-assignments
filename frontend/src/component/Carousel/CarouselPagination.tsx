import React, {
  CSSProperties,
  Children,
  HTMLAttributes,
  useCallback,
  useEffect,
  useState,
} from 'react';
import { classnames, getPrefixCls } from '../../util';
import { UnionOmit } from './interface'
import { useCarousel } from './hooks/useCarousel';
import { useSlideTo } from './hooks/useSlideTo';

export interface ICarouselPagination {
  children?: React.ReactNode;
}

export type ICarouselPaginationProps = UnionOmit<
  ICarouselPagination,
  HTMLAttributes<HTMLDivElement>
>;

export const CarouselPagination = (
  props: ICarouselPaginationProps,
): JSX.Element | null => {
  const { className, ...extra } = props;
  const carousel = useCarousel();
  const handleSlideTo = useSlideTo();
  const {
    speed,
    sliders,
    autoplay,
    currentIndex,
    paginationColor,
    paginationPosition,
    paginationActiveColor,
  } = carousel;
  const [activeIndex, setActiveIndex] = useState<number>(currentIndex);
  const carouselDotsCls = classnames(
    getPrefixCls('carousel-pagination', {
      [paginationPosition]: true,
    }),
    className,
  );
  const sliderCount = sliders.length - 2;

  const handleActiveIndexChange = useCallback((index: number) => {
    setActiveIndex(index);
  }, []);

  const renderPagination = (): JSX.Element[] =>
    Children.map(new Array(sliderCount), (item, index) => {
      const isActive = activeIndex === index;
      const paginationInnerStyle: CSSProperties = {
        backgroundImage: isActive
          ? `linear-gradient(${paginationActiveColor},${paginationActiveColor})`
          : 'none',
      };

      Object.assign(paginationInnerStyle, {
        animationDuration: isActive && autoplay ? `${speed / 1000}s` : '0',
        animationPlayState: autoplay ? 'running' : 'paused',
      });



      return (
        <span
          key={index}
          className={'carousel-pagination-item'}
          style={{backgroundColor: paginationColor}}
          onClick={() => handleSlideTo(index)}
        >
          <span 
            className={getPrefixCls('carousel-pagination-item-inner', {
              active: isActive,
            })}
            style={paginationInnerStyle}
          />
        </span>
      );
    });

  useEffect(() => {
    carousel.addListener('active-index-change', handleActiveIndexChange);

    return () => {
      carousel.removeListener('active-index-change', handleActiveIndexChange);
    };
  }, []);



  if (sliderCount <= 1) {
    return null;
  }

  return (
    <div {...extra} className={carouselDotsCls}>
      {renderPagination()}
    </div>
  );
};
