import React, { useEffect, useState } from "react";
import CarouselItem from "./components/CarouselItem";
import TimeBarItem from "./components/TimeBarItem";
import { ICarouselProps } from "./type";
import { nanoid } from "nanoid";
import "./index.scss";

const Carousel = (props: ICarouselProps) => {
  const { list = [], stayTime = 2, changeTime = 1 } = props;
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    setTimeout(() => {
      if (activeIndex === list.length - 1) {
        setActiveIndex(0);
      } else {
        setActiveIndex(activeIndex + 1);
      }
    }, stayTime * 1000);
  }, [activeIndex]);

  return (
    <div className="comCarousel">
      <div
        className="list"
        style={{
          width: `${100 * list.length}%`,
          transition: `${changeTime}s`,
          transform: `translateX(${-(100 * activeIndex) / list.length}%)`,
        }}
      >
        {list.map((item) => {
          return <CarouselItem key={nanoid()} data={item} />;
        })}
      </div>
      <div className="timeBar">
        {list.map((_, index) => {
          return (
            <TimeBarItem
              stayTime={stayTime}
              active={index === activeIndex}
              key={nanoid()}
            />
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;

export type { ICarouselItem } from "./components/CarouselItem/type";
