import React, { ReactNode, useLayoutEffect, useState } from "react";
import "./index.scss";

interface CarouselProps {
  className?: string;
  children: ReactNode;
  duration: number;
}

interface CarouselSlideProps {
  children: ReactNode;
  className?: string;
}
export function CarouselSlide({ children, className }: CarouselSlideProps) {
  return <div className={`carouselSlide ${className}`}>{children}</div>;
}

function Carousel({ children, className, duration }: CarouselProps) {
  const [X, setX] = useState(0);
  const [width, setWidth] = useState(window.innerWidth);

  function resize() {
    setWidth(window.innerWidth);
  }

  useLayoutEffect(() => {
    const count = React.Children.count(children);
    const tid = setTimeout(() => {
      if (X >= count - 1) {
        setX(0);
      } else {
        setX(X + 1);
      }
    }, duration);

    window.addEventListener("resize", resize, false);
    return () => {
      clearTimeout(tid);
      window.removeEventListener("resize", resize, false);
    };
  }, [X, children, duration]);

  return (
    <div className={`carousel ${className}`}>
      <div
        className="slideWrap"
        style={{
          transitionDuration: "200ms",
          transform: `translateX(-${X * width}px)`,
        }}
      >
        {children}
      </div>
      <div className="paginationWrap">
        {React.Children.map(children, (child, index) => (
          <div className="pagination">
            <div
              className="pagination-active"
              style={{
                animation:
                  X === index ? `progress ${duration / 1000}s linear` : "",
              }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Carousel;
