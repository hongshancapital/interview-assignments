import React, { ReactNode, useEffect, useMemo, useState } from "react";
import "./carousel.css";
import { CarouselItemProps } from "./CarouselItem";

/**
 * Design:
 *
 * CarouselItem内容太定制化，封装为数据props可能格式复杂，使用不便。
 * 以children的形式来书写，会比较形象。
 *
 * <Carousel>控制dot，时间间隔
 * <CarouselItem>控制内容展示
 *
 * 设想
 * <Carousel>
 *    <CarouselItem />
 * 1. 可在子组件改变 dotStyle: backgroundColor / color
 *
 */

interface CarouselProps {
  /** 轮播图容器的样式 */
  className?: string;
  /** 指示器的样式 */
  dotClassName?: string;
  /** 当前图片指示器的样式 */
  dotCurrentClassName?: string;
  /** 仅接收 CarouselItem 组件 */
  children?: ReactNode[];
  /** 控制播放速度。单位ms。 */
  speed?: number;
}

const Carousel: React.FC<CarouselProps> = ({
  children,
  className,
  dotClassName,
  speed,
}) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const childCount = useMemo(
    () => (Array.isArray(children) ? children.length : children ? 1 : 0),
    [children]
  );

  useEffect(() => {
    const timer = setInterval(
      () => setCurrentIndex((currentIndex) => (currentIndex + 1) % childCount),
      speed
    );

    return () => clearInterval(timer);
  }, [childCount, speed]);

  if (childCount < 2) {
    // 从业务出发，保证至少有2个 CarouselItem 能满足合理的需求，同时能避免边界情况。
    // 如确有0、1个child的需求，可以再扩展和放开
    console.warn("Carousel组件需要至少2个child");
    return <div />;
  }

  // FIXME: 对React有点生疏，这里的类型应该不需要any
  const currentItem: CarouselItemProps = (children![currentIndex] as any).props;

  return (
    <div className={className}>
      <div
        className="carousel-items"
        style={{
          transform: `translate(-${currentIndex}00%, 0)`,
        }}
      >
        {children}
      </div>

      <div className="carousel-dot-container">
        {Array(childCount)
          .fill(0)
          .map((v, idx) => (
            <div
              className={`${dotClassName} ${
                currentIndex === idx ? "carousel-dot-current" : ""
              } ${currentItem.dotClassName} ${currentItem.dotCurrentClassName}`}
              key={idx}
            />
          ))}
      </div>
    </div>
  );
};

Carousel.displayName = "Carousel";

Carousel.defaultProps = {
  className: "carousel",
  dotClassName: "carousel-dot",
  dotCurrentClassName: "carousel-dot-current",
  speed: 3000,
};

export { Carousel };
