import React, {
  FC,
  useState,
  useLayoutEffect,
  useRef,
  useMemo,
  useCallback,
} from "react";
import Dot from "./dot";
import "./style.scss";

/**
 * 动画类型
 */
enum Easing {
  LINEAR = "linear",
  EASE_IN = "ease-in",
  EASE_OUT = "ease-out",
  EASE_IN_OUT = "ease-in-out",
}

interface CarouselProps {
  children: React.ReactNode;
  autoplay?: boolean; // 是否自动播放
  autoplayWaitTime?: number; // 自动播放的等待时间
  easing?: Easing; // 动画的类型，就是css样式 transition的动画类型
  className?: string; // 最外层div的className
  style?: React.CSSProperties; // 最外层div的div的style
  width?: number; // 组件的宽度
  height?: number; // 组件的高度
}

const Carousel: FC<CarouselProps> = (props) => {
  const {
    children,
    autoplay = true,
    autoplayWaitTime = 3000,
    easing = Easing.LINEAR,
    style = {},
    className = "",
    width,
    height,
  } = props;
  const [currentWidth, setCurrentWidth] = useState<number>(width || 0);
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const carouselRef = useRef<HTMLDivElement | null>(null);
  const slideWrapper = useRef<HTMLDivElement | null>(null);
  // 等待时间
  const animationStyle = `transform 0.3s ${easing}`;
  const childrenCount: number = React.Children.count(children);
  // 变更slide的方法
  const onSelect: (index: number) => void = useCallback(
    (nextIndex: number) => {
      if (nextIndex >= childrenCount) {
        nextIndex = 0;
      }
      setCurrentIndex(nextIndex);
    },
    [childrenCount]
  );
  // 如果props 中的width 和height发生变化了更新sizeStyle
  const sizeStyle: React.CSSProperties = useMemo(() => {
    const sizeStyle: React.CSSProperties = {};
    if (width && width > 0) {
      sizeStyle.width = `${width}px`;
    }
    if (height && height > 0) {
      sizeStyle.height = `${height}px`;
    }
    return sizeStyle;
  }, [width, height]);

  const nodes = React.Children.toArray(children);
  // 计算  dots 和 slides

  const dots: React.ReactNode[] = [];
  const slides: React.ReactNode[] = [];
  for (let index = 0; index < nodes.length; index++) {
    const child = nodes[index];
    slides.push(
      <div key={index} style={{ width: `${currentWidth}px` }}>
        {child}
      </div>
    );
    dots.push(
      <Dot
        key={index}
        index={index}
        autoplay={autoplay}
        currentIndex={currentIndex}
        autoplayWaitTime={autoplayWaitTime}
        onSelect={onSelect}
      ></Dot>
    );
  }

  // 同步当前真实dom的宽度
  useLayoutEffect(() => {
    const width = carouselRef?.current?.offsetWidth;
    if (width && width !== currentWidth) {
      setCurrentWidth(width);
    }
  }, [carouselRef, width]);
  // 更新当前slide-wrapper的位置
  useLayoutEffect(() => {
    const translateX = -currentIndex * currentWidth;
    if (slideWrapper.current) {
      slideWrapper.current.style.transform = `translateX(${translateX}px)`;
    }
  }, [currentIndex, slideWrapper, currentWidth]);

  return (
    <div
      ref={carouselRef}
      style={{ ...style, ...sizeStyle }}
      className={`carousel ${className}`}
    >
      <div
        ref={slideWrapper}
        className={`slide-wrapper`}
        style={{
          transition: animationStyle,
          width: `${currentWidth * childrenCount}px`,
        }}
      >
        {slides}
      </div>
      <ul className={"dots-wrap dots-wrap-bottom"}>{dots}</ul>
    </div>
  );
};

export default Carousel;
