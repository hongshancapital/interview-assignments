import React, { useState } from 'react';
import { CarouselItem } from './CarouselItem';
import { useAutoPlay } from './hooks/useAutoPlay';
import { useBundleRef } from './hooks/useBundleRef';
import { useObserve } from './hooks/useObserve';
import { Pagination } from './Pagination';
import './style/index.css';

export type CarouselRef = {
  moveTo: (index: number) => void;
  moveToNext: () => void;
  moveToPrevious: () => void;
};

/** 通用轮播图组件的简单实现 */
const Carousel = React.forwardRef<
  CarouselRef,
  {
    children: React.ReactNode;
    auto?: boolean;
    duration?: number;
    onChange?: (index: number) => void;
  }
>(({ children, auto = true, duration = 3000, onChange }, ref) => {
  const childrenArr = React.Children.toArray(children);
  const length = childrenArr.length;

  const [active, setActive] = useState(0);

  const { moveToNext } = useBundleRef({
    ref,
    setActive,
    length,
  });

  useAutoPlay({ ref, auto, duration, active, moveToNext });

  useObserve({ active, length, onChange });

  return (
    <div className="container">
      <div className="carousel" id="carousel">
        {childrenArr.map((item, index) => {
          return (
            <CarouselItem key={index} id={`carouse_item_${index}`}>
              {item}
            </CarouselItem>
          );
        })}
      </div>
      <Pagination active={active} count={length} duration={duration} />
    </div>
  );
});

export default Carousel;
