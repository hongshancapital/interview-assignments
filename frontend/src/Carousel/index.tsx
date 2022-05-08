import React, {
  useEffect,
  useMemo,
  useState,
  forwardRef,
  useImperativeHandle,
} from "react";
import Indicator from "./Indicator";
import "./index.css";

export interface CarouselProps {
  autoPlay?: boolean;
  interval?: number;
  children?: React.ReactChild[];
}

export interface CarouselRef {
  activeIndex: number;
}

const Carousel = forwardRef(
  ({ autoPlay = true, interval = 3000, children = [] }: CarouselProps, ref) => {
    const [activeIndex, setActiveIndex] = useState(0);

    // Computed style
    const style = useMemo(
      () => ({
        transform: `translate3d(-${activeIndex * 100}vw, 0, 0)`,
      }),
      [activeIndex]
    );

    // Auto play
    useEffect(() => {
      if (!autoPlay) return;
      const timer = setInterval(() => {
        setActiveIndex((activeIndex + 1) % children.length);
      }, interval);
      return () => clearInterval(timer);
    });

    useImperativeHandle(
      ref,
      () => ({
        activeIndex,
      }),
      [activeIndex]
    );

    return (
      <div className="carousel">
        <div className="carousel-body" style={style}>
          {children}
        </div>
        <Indicator
          activeIndex={activeIndex}
          count={children.length}
          onChange={(index) => setActiveIndex(index)}
        />
      </div>
    );
  }
);

export default Carousel;
