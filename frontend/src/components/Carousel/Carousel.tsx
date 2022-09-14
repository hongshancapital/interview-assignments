import React, { useState, useEffect, memo } from "react";
import "../../styles/Carousel.css";
import CarouselItem from "./CarouselItem";
import type { CarouselPropsType } from "../../types/CarouselType";

const Carousel = ({
  defaultIndex = 0,
  source = [],
  style = {},
  timer = 3000,
}: CarouselPropsType) => {
  const [activeIndex, setActiveIndex] = useState(defaultIndex);
  const duration = timer / 1000;

  const replayAnimations = () => {
    document.getAnimations().forEach((item) => {
      item.cancel();
      item.play();
    });
  };

  const onUpdateIndex = (newIndex: number) => {
    // 当超出索引时重置为0
    if (newIndex >= source.length) {
      newIndex = 0;
    }
    setActiveIndex(newIndex);
    replayAnimations();
  };

  // 响应点击行为
  const onClickCarouselIndex = (index: number) => {
    onUpdateIndex(index);
    replayAnimations();
  };

  useEffect(() => {
    const interval = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, timer);

    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  });

  if (!Array.isArray(source) || source.length === 0) return null;

  return (
    <div className="container" style={style}>
      <div
        className="content"
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {source.map(({ id, ...sourceItem }) => (
          <CarouselItem key={id} {...sourceItem} />
        ))}
      </div>
      <div className="loading">
        {source.map((sourceItem, index) => (
          <div
            className="loading_outer"
            onClick={() => onClickCarouselIndex(index)}
            key={sourceItem.id}
          >
            <div
              className="loading_inside"
              style={{
                animationDuration: index === activeIndex ? `${duration}s` : "",
                backgroundColor: index === activeIndex ? "#fff" : "",
              }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

Carousel.displayName = "Carousel";

export default memo(Carousel);
