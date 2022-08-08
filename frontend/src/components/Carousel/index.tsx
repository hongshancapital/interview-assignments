import React, { useEffect, useState } from "react";
import CarouserItem from "../CarouselItem";
import { ICarouselItem } from "../../types/carousel";
import "./index.scss";

export default function Carousel(props: {
  duration?: number; // 动画停留时间
  items?: Array<ICarouselItem>; // 轮播内容
}) {
  const { duration = 500, items = [] } = props;
  const dotDuration = duration * items.length;

  // 当前图片
  const [activeIndex, setActiveIndex] = useState(0);
  useEffect(() => {
    let count = 0;
    //轮播切换
    const timmer = setInterval(() => {
      count = ++count % items.length;
      setActiveIndex(count);
    }, dotDuration);
    return () => {
      clearInterval(timmer);
    };
  }, [dotDuration, items.length]);

  return (
    <div className="carousel">
      {/* 轮播图 */}
      <div
        className="carousel__container"
        style={{
          transitionDuration: duration + "ms",
          transform: `translateX(-${activeIndex * 100}%)`,
        }}
      >
        {items.map((item, index) => {
          return (
            <div key={index} className="carousel__carousel-item">
              <CarouserItem child={item}></CarouserItem>
            </div>
          );
        })}
      </div>
      {/* 底部轮播指示器 */}
      <div className="carousel__step">
        {items.map((item, index) => {
          return (
            <div
              className={`carousel__step-item ${
                index === activeIndex ? "active" : ""
              }`}
              style={{ animationDuration: duration + "ms" }}
              key={index}
            ></div>
          );
        })}
      </div>
    </div>
  );
}
