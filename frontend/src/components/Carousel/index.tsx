import React, { ReactNode, useEffect, useState } from 'react';
import './index.scss';

interface CarouselProps {
  style?: React.CSSProperties;
  children?: ReactNode;
}
const oneThousandMilliseconds = 1000;

export default function Carousel(prop: CarouselProps) {
  const { children, style } = prop;
  const childrenArray = React.Children.toArray(children);
  const [currentPageIndex, setCurrentPageIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentPageIndex((currentPageIndex) =>
        currentPageIndex === childrenArray.length - 1
          ? 0
          : currentPageIndex + 1,
      );
    }, 3 * oneThousandMilliseconds);

    return () => {
      clearInterval(timer);
    };
  }, [childrenArray.length]);

  return (
    <div className="carousel" style={style}>
      <div
        className="carousel-list"
        style={{ transform: `translateX(-${currentPageIndex * 100}%)` }}
      >
        {childrenArray.map((child, index) => (
          <div className="carousel-list-item" key={index}>
            {child}
          </div>
        ))}
      </div>
      {childrenArray.length && (
        <div className="carousel-dots">
          {childrenArray.map((child, index) => (
            <div
              className={`carousel-dot ${currentPageIndex === index ? 'cur':''}`}
              key={index}
            ></div>
          ))}
        </div>
      )}
    </div>
  );
}
