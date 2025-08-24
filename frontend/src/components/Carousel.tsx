import { createContext, ReactNode, useEffect, useState } from "react";

import "../css/Carousel.css";

export interface CarouselSize {
  width: string;
  height: string;
}

export const SizeContext = createContext({
  width: "200px",
  height: "100px",
});

export interface CarouselProps {
  width: string;
  height: string;
  children: ReactNode[];
}

export function Carousel(props: CarouselProps) {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const { length: childrenLength } = props.children;
    // when no child, do not start animation
    if (childrenLength < 1) return;
    // start animation
    const timeout = setTimeout(() => {
      setCurrentIndex(
        currentIndex === childrenLength - 1 ? 0 : currentIndex + 1,
      );
      console.log(currentIndex);
    }, 3000);

    return () => {
      clearTimeout(timeout);
    };
  }, [currentIndex, props.children]);

  return (
    <div
      className="carousel"
      style={{
        width: props.width,
        height: props.height,
      }}
    >
      <div
        className="carousel-slides"
        style={{
          left: currentIndex > 0 ? `${currentIndex * -100}%` : "0",
        }}
      >
        <SizeContext.Provider
          value={{
            height: props.height,
            width: props.width,
          }}
        >
          {props.children}
        </SizeContext.Provider>
      </div>

      <div className="indicators">
        {props.children.map((_: any, index: number) => (
          <div
            key={index}
            className={`indicator ${index === currentIndex ? "active" : ""}`}
          ></div>
        ))}
      </div>
    </div>
  );
}
