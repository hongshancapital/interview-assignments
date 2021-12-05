import React, { useState, useEffect } from "react";
import "./index.css";

import Indicator from "./components/Indicator";
const defaultProps = {
  showIndicator: true,
  delay: 3000,
  initialIndex: 0,
};
export type CarouselProps = {
  itemList: Array<string>;
} & typeof defaultProps;

const Carousel = (props: CarouselProps) => {
  const [currentIndex, setCurrentIndex] = useState(props.initialIndex);
  const list = props.itemList;
  const itemLength = list.length;
  const delay = props.delay;

  useEffect(() => {
    const timer = setTimeout(() => {
      setCurrentIndex(currentIndex + 1 === itemLength ? 0 : currentIndex + 1);
    }, delay);
    return () => clearTimeout(timer);
  }, [currentIndex, delay, itemLength]);

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel-content"
        style={{
          transform: `translateX(${currentIndex * 100 * -1}%)`,
          width: `${100 * itemLength}%`,
        }}
      >
        {list.map((item) => (
          <div key={item} className="img-wrapper">
            <img className="image" alt="img" src={item} />
          </div>
        ))}
      </div>
      {props.showIndicator ? (
        <Indicator
          length={itemLength}
          delay={delay}
          index={currentIndex}
        ></Indicator>
      ) : null}
    </div>
  );
};

Carousel.defaultProps = defaultProps;

export default Carousel;
