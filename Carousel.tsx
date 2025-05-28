import React, {
  useState,
  forwardRef,
  useEffect,
  useImperativeHandle,
} from "react";
import { useCarousel } from "./helper";
import "./style.css";

export interface CarouselProps {
  children?: React.ReactNode;
  autoplay?: boolean;
  loop?: boolean;
}

export interface CarouselRef {
  goTo?: (index: number) => void;
  next?: () => void;
  prev?: () => void;
}

const Carousel = forwardRef<CarouselRef, CarouselProps>(
  ({ children, autoplay = false, loop = true}, ref) => {
    const [count, setCount] = useState(React.Children.count(children));
    const { offset, prev, next, goTo } = useCarousel({
      total: count,
      autoplay,
      loop,
    });
    useEffect(() => {
      const newCount = React.Children.count(children);
      if (count !== newCount) {
        goTo(0);
        setCount(newCount);
      }
    }, [children]);
    useImperativeHandle(ref, () => ({ offset, goTo, prev, next }));
    return (
      <div className="carousel-container">
        <div
          className="scroller"
          style={{
            transform: `translate3d(-${offset * 300}px,0,0)`,
            width: `${count * 300}px`,
          }}
        >
          {children}
        </div>
      </div>
    );
  }
);

export { Carousel as default, useCarousel };
