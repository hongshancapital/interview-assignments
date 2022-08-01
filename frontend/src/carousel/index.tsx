import React, { CSSProperties, ReactElement, useEffect, useState } from "react";
import "./index.css";

export interface CarouselProps {
  /**每幅轮播持续时间  毫秒，默认1000*/
  duration?: number;
  /**滚动动画的持续时间 毫秒，默认500 */
  transitionDuration?: number;
  /**配置轮播项 */
  items: ReactElement[];
  /**设置容器的属性 */
  style?: CSSProperties;
}
export default function Carousel(props: CarouselProps) {
  const {
    duration = 2000,
    transitionDuration = 500,
    items = [],
    style = {},
  } = props;
  //标识当前轮播到第几张图片
  const [activeIndex, setActiveIndex] = useState(0);
  useEffect(() => {
    let count = 0;
    //轮播切换
    let timmer = setInterval(() => {
      count = ++count % items.length;
      setActiveIndex(count);
    }, duration);
    return () => {
      window.clearInterval(timmer);
    };
  }, [duration, items.length, transitionDuration]);
  return (
    <div className="carousel" style={style}>
      {/* 轮播图 */}
      <div
        className="carousel__container"
        style={{
          transitionDuration: transitionDuration + "ms",
          transform: `translateX(-${activeIndex * 100}%)`,
        }}
      >
        {props.items.map((item, index) => {
          return (
            <div key={index} className="carousel__carousel-item">
              {item}
            </div>
          );
        })}
      </div>
      {/* 轮播指示器 */}
      <div className="carousel__step">
        {items.map((item, index) => {
          return (
            <div
              className={`carousel__step__step-item ${
                index === activeIndex ? "active" : ""
              }`}
              style={{ animationDuration: duration + "ms" }}
              key={item.key}
            ></div>
          );
        })}
      </div>
    </div>
  );
}
