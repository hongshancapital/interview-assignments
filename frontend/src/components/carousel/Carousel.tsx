import React, { PropsWithChildren, useEffect, useMemo, useState } from "react";
import { CarouselProps } from "./interface";
import CarouselItem from "./CarouselItem";
import { ReactElement } from "react";
import CarouselContext from "./context";
import CarouselDot from "./CarouselDot";
import "./index.css";

function Carousel({
  height,
  duration,
  autoplay = true,
  className = "",
  style,
  children,
}: PropsWithChildren<CarouselProps>) {
  const [currentIndex, setCurrentIndex] = useState(-1);
  const [reCountdown, setReCountdown] = useState(1);
  const items = useMemo(() => {
    const res: Array<typeof CarouselItem> = [];
    React.Children.forEach(children, (child: any) => {
      if (child.type === CarouselItem) {
        res.push(child);
      }
    });
    return res;
  }, [children]) as unknown as ReactElement[];

  const go = (index: number) => {
    setCurrentIndex(index);
    setReCountdown((v) => v + 1);
  };

  useEffect(() => {
    if (!autoplay) return;
    const timer = window.setInterval(() => {
      setCurrentIndex((v) => {
        if (v >= items.length - 1) {
          return 0;
        }
        return v + 1;
      });
    }, duration);
    return () => {
      window.clearInterval(timer);
    };
  }, [autoplay, duration, items.length, reCountdown]);

  useEffect(() => {
    setCurrentIndex(0);
  }, []);

  return (
    <CarouselContext.Provider
      value={{ go, activeIndex: currentIndex, duration }}
    >
      <div className={`${className} carousel`} style={{ ...style, height }}>
        <div
          className="carousel-items-container"
          style={{
            transform: `translate3D(-${currentIndex * 100 + "%"}, 0px, 0px)`,
            height,
          }}
        >
          {items}
        </div>
        <CarouselDot count={items.length} />
      </div>
    </CarouselContext.Provider>
  );
}
Carousel.displayName = "Carousel";
Carousel.Item = CarouselItem;
export default Carousel;
