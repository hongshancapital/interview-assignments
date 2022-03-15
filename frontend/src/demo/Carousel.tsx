import React, { FC, useState, useCallback, useEffect } from "react";
import "./style.css";

const Item: FC<{ src: string }> = ({ src }) => {
  return (
    <div className="slider-item">
      <img src={src} alt="" />
    </div>
  );
};

const Carousel: FC<{
  containerRect: { height: string; width: string };
  pictureArr: Array<{ src: string; id: number }>;
}> = ({ containerRect, pictureArr }) => {
  const [activeIndex, setActiveIndex] = useState(-1);
  const translateX = `-${100 * activeIndex}%`;

  const runTimer = useCallback(() => {
    // 触发第一次的 transition 动画
    if (activeIndex === -1) {
      setActiveIndex(0);
    } else {
      setTimeout(() => {
        setActiveIndex((activeIndex + 1) % pictureArr.length);
      }, 2500);
    }
  }, [activeIndex, pictureArr]);

  useEffect(() => {
    runTimer();
  }, [runTimer]);

  return (
    <div
      className="container"
      style={{ height: containerRect.height, width: containerRect.width }}
    >
      <div className="notice">
        Demo 说明：直接用了 assets 里的图片实现了轮播的功能，图片本身与 mov
        中示例不同的问题就不处理了
      </div>
      <div
        className="slider"
        style={{ transform: `translateX(${translateX})` }}
      >
        {pictureArr.map((i) => (
          <Item key={i.id} src={i.src} />
        ))}
      </div>
      <div className="progress">
        {pictureArr.map((i, index) => (
          <div
            key={i.id}
            className={`progress-item ${activeIndex === index && "active"}`}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
