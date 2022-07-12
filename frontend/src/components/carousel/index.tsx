import React, { ReactNode, useEffect, useState } from "react";
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
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const count = React.Children.count(children);
    const tid = setTimeout(() => {
      if (index >= count - 1) {
        setIndex(0);
      } else {
        setIndex(index + 1);
      }
    }, duration);

    return () => {
      clearTimeout(tid);
    };
  }, [index, children, duration]);

  return (
    <div className={`carousel ${className}`}>
      <div
        className="slideWrap"
        style={{
          transitionDuration: "200ms",
          transform: `translateX(-${index * 100}%)`,
        }}
      >
        {children}
      </div>
      <div className="paginationWrap">
        {React.Children.map(children, (child, idx) => (
          <div className="pagination">
            <div
              className="pagination-active"
              style={{
                animation:
                index === idx ? `progress ${duration / 1000}s linear` : "",
              }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Carousel;
