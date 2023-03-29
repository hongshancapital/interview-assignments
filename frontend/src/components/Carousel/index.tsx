import React, { FC, useEffect, useMemo, useRef, useState } from "react";
import { CarouselProps } from "./interface";
import "./index.css";

export * from "./interface";
export * from "./data";

export const Carousel: FC<CarouselProps> = (props) => {
  const { data, duration = 3000 } = props;

  const [activeIndex, setActiveIndex] = useState<number>(0);
  const [width, setWidth] = useState<string | number>("100%");
  const timer = useRef<NodeJS.Timeout | undefined>();
  const bgRef = useRef<HTMLDivElement>(null);

  const startIntervalTime = () => {
    timer.current = setInterval(() => {
      setActiveIndex((index) => (index >= data.length - 1 ? 0 : index + 1));
    }, duration);
  };

  const { transform, carouselWidth } = useMemo(() => {
    if (typeof width === "string")
      return { transform: `translateX(0)`, carouselWidth: "100%" };
    return {
      transform: `translateX(-${width * activeIndex}px)`,
      carouselWidth: width * data.length,
    };
  }, [width, activeIndex]);

  useEffect(() => {
    startIntervalTime();
    if (bgRef.current?.clientWidth) {
      setWidth(bgRef.current?.clientWidth);
    }
    return () => clearInterval(timer.current);
  }, []);

  return (
    <div className="carousel-bg" ref={bgRef}>
      <ul
        className="carousel"
        style={{
          transform,
          width: carouselWidth,
        }}
      >
        {data.map((item) => {
          return (
            <li className="carousel-item" key={item.id} style={{ width }}>
              <img className="carousel-item-image" src={item.bgSrc} alt="" />
              <p className="title">{item.title}</p>
              {item.description && (
                <span className="text">{item.description}</span>
              )}
            </li>
          );
        })}
      </ul>

      <div className="indicators">
        {data.map((i, index) => {
          return (
            <div
              className={`indicator ${index === activeIndex ? "active" : ""}`}
              key={"indicator_" + i.id}
            />
          );
        })}
      </div>
    </div>
  );
};
