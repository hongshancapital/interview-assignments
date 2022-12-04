import {
  type FC,
  type CSSProperties,
  type PropsWithChildren,
  Children,
  useState,
  useMemo,
  useRef,
  useCallback,
  useEffect,
  useLayoutEffect,
} from "react";
import clns from "classnames";

import { isFunction } from "../utils/isFunction";
import SlickCarousel from "./SlickCarousel";
import Indicator from "./Indicator";

import styles from "./index.module.scss";

export interface CarouselProps {
  /** 自定义样式 */
  style?: CSSProperties;

  /** 自定义类名 */
  customClassName?: string;

  /**
   * 是否自动播放
   * @default true
   */
  autoplay?: boolean;

  /**
   * 控制显隐指示器组件
   * @default true
   */
  showIndicator?: boolean;

  /**
   * 当前轮播索引值
   * @default 0
   */
  currentIndex?: number;

  /**
   * 当前轮播图停留时长，单位 ms
   * @default 3000
   */
  duration?: number;

  /**
   * 切换轮播图时转场时延，单位 ms
   * @default 500
   */
  speed?: number;

  /**
   * 切换轮播图时动画效果
   * @default linear
   */
  easing?: CSSProperties["transitionTimingFunction"];

  /**
   * 切换轮播回调
   * @param { number } current 当前轮播索引值
   */
  onChange?: (current: number) => void;
}

const Carousel: FC<PropsWithChildren<CarouselProps>> = ({
  autoplay = true,
  showIndicator = true,
  currentIndex = 0,
  duration = 3000,
  speed = 500,
  easing = "linear",
  style,
  customClassName,
  onChange,
  children,
}) => {
  const carouselCount = useMemo(
    () => Children.count(children) || 0,
    [children]
  );
  const [activeIndex, setActiveIndex] = useState(0);

  let carouselTimer = useRef<ReturnType<typeof setTimeout> | null>(null);

  const clearCarouselTimer = useCallback(() => {
    if (carouselTimer.current) {
      clearTimeout(carouselTimer.current);
      carouselTimer.current = null;
    }
  }, []);

  useLayoutEffect(() => {
    if (!carouselCount || currentIndex === undefined) return;
    const rangedCurrentIndex = Math.min(
      Math.max(0, currentIndex),
      carouselCount - 1
    );
    setActiveIndex(rangedCurrentIndex);
  }, [currentIndex, carouselCount]);

  useEffect(() => {
    isFunction(onChange) && onChange(activeIndex);
  }, [activeIndex]);

  useEffect(() => {
    if (carouselCount > 1 && autoplay) {
      carouselTimer.current = setTimeout(() => {
        setActiveIndex(
          (prevActiveIndex) => (prevActiveIndex + 1) % carouselCount
        );
      }, duration);
    }

    return clearCarouselTimer;
  }, [activeIndex, carouselCount, autoplay, duration, clearCarouselTimer]);

  const handleClickIndicator = useCallback((index: number) => {
    setActiveIndex(index);
  }, []);

  return (
    <div
      className={clns(styles.carousel, customClassName)}
      style={{ ...(style ?? {}) }}
      data-testid="carousel"
    >
      <SlickCarousel speed={speed} activeIndex={activeIndex} easing={easing}>
        {children}
      </SlickCarousel>
      {showIndicator && (
        <Indicator
          count={carouselCount}
          activeIndex={activeIndex}
          duration={duration}
          onClick={handleClickIndicator}
        />
      )}
    </div>
  );
};

export default Carousel;
