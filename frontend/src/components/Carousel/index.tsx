import React, { useState, useEffect, useRef, useImperativeHandle } from "react";
import classnames from "classnames";
import "./index.css";

export interface CarouselProps {
  children: React.ReactNode;
  autoplay?: boolean;
  loop?: boolean;
  duration?: number;
  style?: React.CSSProperties;
}

export interface CarouselRef {
  next: () => void;
  prev: () => void;
  pause: () => void;
  play: () => void;
  change: (index: number) => void;
  getIndex: () => number;
}

const Carousel = React.forwardRef<CarouselRef, CarouselProps>(
  (
    {
      children,
      loop = true,
      autoplay = true,
      duration = 3000,
      style,
      ...props
    },
    ref
  ) => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const timeoutRef = useRef(0);
    const childArray = React.Children.toArray(children);

    const changeTab = (index: number) => {
      setCurrentIndex(index);
    };

    // todo: keydown
    useImperativeHandle(ref, () => ({
      prev: () => {
        if (timeoutRef.current) clearTimeout(timeoutRef.current);

        if (currentIndex === 0) {
          setCurrentIndex(childArray.length - 1);
        } else {
          setCurrentIndex(currentIndex - 1);
        }
      },
      next: () => {
        if (timeoutRef.current) clearTimeout(timeoutRef.current);

        if (currentIndex === childArray.length - 1) {
          setCurrentIndex(0);
        } else {
          setCurrentIndex(currentIndex + 1);
        }
      },
      play: () => {
        if (timeoutRef.current) clearTimeout(timeoutRef.current);

        if (currentIndex === childArray.length - 1) {
          setCurrentIndex(0);
        } else {
          setCurrentIndex(currentIndex + 1);
        }
      },
      pause: () => {
        if (timeoutRef.current) clearTimeout(timeoutRef.current);
      },
      change: changeTab,
      getIndex: () => currentIndex,
    }));

    useEffect(() => {
      if (!autoplay) return;

      const next = () => {
        if (timeoutRef.current) {
          clearTimeout(timeoutRef.current);
        }

        if (currentIndex > childArray.length - 2 && loop) {
          setCurrentIndex(0);
        } else {
          setCurrentIndex(currentIndex + 1);
        }
      };

      timeoutRef.current = window.setTimeout(
        () => requestAnimationFrame(next),
        duration
      );
      return () => {
        if (timeoutRef.current) {
          clearTimeout(timeoutRef.current);
        }
      };
    }, [autoplay, duration, currentIndex, childArray, loop]);

    return (
      <div className="carousel-wrap" style={style} {...props}>
        {childArray.map((child, index) => (
          <div
            key={index}
            className="carousel-content"
            style={{
              left: 100 * index + "%",
              transform: `translateX(${-100 * currentIndex}%)`,
            }}
          >
            {child}
          </div>
        ))}
        <div className="carousel-controls">
          {childArray.map((child, index) => (
            <div
              key={index}
              className="carousel-line"
              onClick={() => changeTab(index)}
            >
              <div
                className={classnames("carousel-hover", {
                  "carousel-process": index === currentIndex,
                })}
                style={{
                  animationDuration: duration + "ms",
                }}
              />
            </div>
          ))}
        </div>
      </div>
    );
  }
);

export default Carousel;
