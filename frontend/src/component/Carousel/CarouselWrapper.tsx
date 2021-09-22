import React, {
  CSSProperties,
  Children,
  HTMLAttributes,
  cloneElement,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { UnionOmit } from './interface';
import { useElementSize, useRefGetter } from '../../hooks';
import { useCarousel } from './hooks/useCarousel';
import { CarouselSlider } from './CarouselSlider';
import { classnames, IElementSize, getPrefixCls } from '../../util';

export interface ICarouselWrapper {
  onActiveChange?: (currentIndex: number) => void;
  children?: React.ReactNode;
}

export type ICarouselWrapperProps = ICarouselWrapper &
  UnionOmit<ICarouselWrapper, HTMLAttributes<HTMLDivElement>>;

interface CarouselWrapperRefType {
  goTo: (nextIndex: number) => void;
  prev: () => void;
  next: () => void;
}

const containerCls = getPrefixCls('carousel-container');

export const CarouselWrapper = React.forwardRef<
  CarouselWrapperRefType,
  ICarouselWrapperProps
>((props, ref) => {
  const { onActiveChange, className, children, ...extra } = props;

  const carousel = useCarousel();
  const containerRef = useRef<HTMLDivElement>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  const wrapperCls = classnames(getPrefixCls('carousel-wrapper'), className);
  const [ready, setReady] = useState(false);

  React.useImperativeHandle(ref, () => ({
    goTo: (nextIndex) => carousel.slideTo(nextIndex),
    prev: () => {
      if (carousel.sliders.length <= 1) {
        carousel.slideTo(0);
      } else {
        carousel.slideTo(carousel.currentIndex - 1);
      }
    },
    next: () => carousel.slideTo(carousel.currentIndex + 1),
  }));



  const handleTranslate = useCallback((style: CSSProperties) => {
    Object.assign((containerRef.current as HTMLDivElement).style, style);
  }, []);

  const handleInitContainer = useCallback((carouselSize: IElementSize) => {
    const { sliders } = carousel;
    const containerDom = containerRef.current as HTMLDivElement;
    const slidersDom = containerDom.children;

    // 初始化容器&sliders
    Object.assign(containerDom.style, {
      width: `${sliders.length * carouselSize.width}px`,
    });
    for (const slider of slidersDom as any) {
      Object.assign(slider.style, {
        width: '100%',
      });
    }
  }, []);

  const handleActiveIndexChange = useRefGetter((currentIndex: number) => {
    onActiveChange && onActiveChange(currentIndex);
  });

  const handleFilterChild = useCallback(() => {
    const sliders: React.ReactNode[] = [];
    const others: React.ReactNode[] = [];

    Children.map(children, (child: any) => {
      if (child.type === CarouselSlider) {
        sliders.push(child);
      } else {
        others.push(child);
      }
    });
    return [sliders, others];
  }, [children]);

  const [originalChild, otherChild] = handleFilterChild();

  useEffect(() => {
    const handleActiveChange = handleActiveIndexChange();

    carousel.addListener('begin-translate', handleTranslate);
    carousel.addListener('active-index-change', handleActiveChange);
    carousel.addListener('carousel-size-change', handleInitContainer);

    return () => {
      carousel.removeListener('begin-translate', handleTranslate);
      carousel.removeListener('active-index-change', handleActiveChange);
      carousel.removeListener('carousel-size-change', handleInitContainer);
    };
  }, []);

  useEffect(() => {
    carousel.initCarouselSize();
    carousel.initcurrentIndex();
  }, [children]);

  useElementSize(
    wrapperRef,
    () => true,
    (wrapperSize: IElementSize) => {
      setReady(true);
      carousel.setCarouselSize(wrapperSize);
    },
  );

  carousel.cloneChild(originalChild);

  return (
    <div ref={wrapperRef} className={wrapperCls} {...extra}>
      <div ref={containerRef} className={containerCls}>
        {ready &&
          Children.map(carousel.sliders, (child: any, index) =>
            cloneElement(child, {
              key: index,
            }),
          )}
      </div>
      {otherChild}
    </div>
  );
});
