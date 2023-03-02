/*
 * @Description: 轮播图 容器视图
 * @Author: cmh
 * @Date: 2023-03-02 14:15:05
 * @LastEditTime: 2023-03-02 14:44:58
 * @LastEditors: cmh
 */
import React, { useState, useEffect } from "react";
import style from "./Carousel.module.css";
/**
 * @param {children} children ReactNode
 * @param {switchingTime} switchingTime 间隔时间 默认3秒 以毫秒为单位 3000ms = 3s
 * @returns 轮播图 容器
 */
const Carousel = ({
  children = React.createElement("div"),
  switchingTime = 3000,
}) => {
  const time = ((switchingTime % 60000) / 1000).toFixed(0); // 将毫秒转换为秒
  const [activeIndex, setActiveIndex] = useState(0); // 对应索引

  /**
   * 更新索引
   * @param {newIndex} newIndex 更新索引
   */
  const onUpdateIndex = (newIndex:any) => {
    if (newIndex < 0) {
      newIndex = React.Children.count(children) - 1;
    } else if (newIndex >= React.Children.count(children)) {
      newIndex = 0;
    }
    setActiveIndex(newIndex);
    replayAnimations();
  };

  /**
   * 重置动画
   */
  const replayAnimations = () => {
    document.getAnimations().forEach((anim) => {
      anim.cancel();
      anim.play();
    });
  };


  useEffect(() => {
    const interval = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, switchingTime);

    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  });

  return (
    <div className={style.container}>
      <div
        className={style.inner}
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {React.Children.map(children, (child) => {
          return React.cloneElement(child, { width: "100%", height: "100vh" } as any);
        })}
      </div>
      <div className={style.loading}>
        {React.Children.map(children, (child, index) => {
          return (
            <div
              className={style.indicator_outer}
            >
              <div
                className={style.indicator_inside}
                style={{
                  animationDuration: index === activeIndex ? `${time}s` : "0s",
                  backgroundColor: index === activeIndex ? "rgba(255,255,255,0.5)" : undefined,
                }}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
