import {
  type FC,
  type PropsWithChildren,
  type CSSProperties,
  Children,
  useRef,
  useState,
  useLayoutEffect,
  useMemo,
  memo,
  useEffect,
} from "react";
import { throttle } from "../../utils/throttle";

import styles from "./index.module.scss";

export interface SlickCarouselProps {
  activeIndex: number;
  speed: number;
  easing: CSSProperties["transitionTimingFunction"];
}

const SlickCarouselSlideItem: FC<
  PropsWithChildren<{ style?: CSSProperties }>
> = ({ style, children }) => {
  return (
    <div
      className={styles.slick_carousel_slide_item}
      style={{ ...(style ?? {}) }}
    >
      {children}
    </div>
  );
};

const SlickCarousel: FC<PropsWithChildren<SlickCarouselProps>> = memo(
  ({ activeIndex, speed, easing, children }) => {
    const [carouselWidth, setCarouselWidth] = useState(0);
    const slickCarouselRef = useRef<HTMLDivElement | null>(null);

    const slickCarouselItems = useMemo(() => {
      if (!children) return [];
      const slideItemStyle = { width: carouselWidth };
      if (Array.isArray(children) && children.length)
        return Children.map(children, (child) => (
          <SlickCarouselSlideItem style={slideItemStyle}>
            {child}
          </SlickCarouselSlideItem>
        ));
      return (
        <SlickCarouselSlideItem style={slideItemStyle}>
          {children}
        </SlickCarouselSlideItem>
      );
    }, [children, carouselWidth]);

    const slickCarouselSlideWrapperStyle = useMemo(() => {
      return {
        transform: `translate3d(${-activeIndex * carouselWidth}px, 0, 0)`,
        transitionDuration: `${speed}ms`,
        transitionTimingFunction: easing,
      };
    }, [activeIndex, carouselWidth, speed, easing]);

    useLayoutEffect(() => {
      setCarouselWidth(slickCarouselRef.current?.offsetWidth ?? 0);
    }, []);

    useEffect(() => {
      const handleResize = throttle(() => {
        if (slickCarouselRef.current) {
          setCarouselWidth(slickCarouselRef.current.offsetWidth);
        }
      });
      window.addEventListener("resize", handleResize);

      return () => {
        window.removeEventListener("resize", handleResize);
      };
    }, []);

    return (
      <div className={styles.slick_carousel} ref={slickCarouselRef}>
        <div
          className={styles.slick_carousel_slide_wrapper}
          style={slickCarouselSlideWrapperStyle}
        >
          {slickCarouselItems}
        </div>
      </div>
    );
  }
);

export default SlickCarousel;
