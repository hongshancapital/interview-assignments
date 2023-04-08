import React, { useEffect, useLayoutEffect, useRef } from "react";
import type { FC, PropsWithRef, ReactElement } from "react";
import CarouselItem, {
  CarouselItemProps,
  CarouselItemRef,
} from "./carousel-item";
import "./index.css";
import useCarousel from "./hooks/use-carousel";
import CarouselDots from "./carousel-dots";
import CarouselItemContent from "./carousel-item-content";

export interface CarouselProps {
  /**
   * @description 是否自动切换
   * @default false
   */
  autoplay?: boolean;
  /**
   * @description 如开启自动切换,该属性生效,设置自动切换的时间间隔,单位:ms
   * @default 3000
   */
  autoplayInterval?: number;
  /**
   * @description 是否自动循环
   * @default false
   */
  loop?: boolean;
  /**
   * @description 轮播切换动画持续时长
   * @default 1000
   */
  duration?: number;
  /**
   * @description 是否显示底部指示器
   * @default true
   */
  showDots?: boolean;
  /**
   * @description 轮播的子元素
   */
  children: Array<ReactElement<PropsWithRef<CarouselItemProps>>>;
}

const defaultProps = {
  autoplay: false,
  autoplayInterval: 3000,
  loop: false,
  duration: 1000,
  showDots: true,
};

const Carousel: FC<CarouselProps> & {
  Item: typeof CarouselItem;
  CarouselItemContent: typeof CarouselItemContent;
} = (props) => {
  const { children, loop, autoplayInterval, autoplay, duration, showDots } = {
    ...defaultProps,
    ...props,
  };

  const count = React.Children.count(children);
  const carouselRef = useRef<HTMLDivElement>(null);

  const {
    setContainerWidth,
    containerWidth,
    carouselContentRef,
    setCarouselListRefByIdx,
    startLoop,
    stopLoop,
    activeIdx,
  } = useCarousel({
    count,
    duration,
    loop,
    interval: autoplayInterval,
  });

  useEffect(() => {
    if (autoplay) {
      startLoop();
    }

    return () => {
      stopLoop();
    };
  }, [autoplay, startLoop, stopLoop]);

  useLayoutEffect(() => {
    if (carouselRef.current) {
      setContainerWidth(carouselRef.current.getBoundingClientRect().width);
    }
  }, [setContainerWidth]);

  return (
    <div className="carousel" ref={carouselRef}>
      <div
        className="carousel-content"
        ref={carouselContentRef}
        style={{ width: count * containerWidth }}
      >
        {React.Children.map(children, (item, idx) => {
          if (!React.isValidElement(item)) return null;
          if (item.type.toString() !== CarouselItem.toString()) {
            return null;
          }

          return React.cloneElement(item, {
            style: { width: containerWidth },
            ref: (ref: CarouselItemRef) =>
              setCarouselListRefByIdx({ idx, value: ref }),
          });
        })}
      </div>

      {showDots && (
        <CarouselDots
          count={count}
          autoplayInterval={autoplayInterval}
          activeIdx={activeIdx}
        />
      )}
    </div>
  );
};

Carousel.Item = CarouselItem;
Carousel.CarouselItemContent = CarouselItemContent;

export default Carousel;
