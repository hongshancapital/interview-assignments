/*
 * @Description: 轮播组件子元素
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-07 18:11:39
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-10 23:40:37
 */

import React, { useMemo } from "react";
import { CarouselItemProps } from "../types";
import "./index.css";
import { useCarouselContext } from "../CarouselContext";
const CarouselItem = (props: CarouselItemProps) => {
  const {
    children,
    style,
    className = "",
    index = 0 
  } = props;
  const { current, isFlip,animationSecends } = useCarouselContext();
  const clsName =
    (current === index) ? "carousel-item-active" : "carousel-item-deactive";
  const animation = useMemo(() => {
    if (!isFlip) {
      return "none";
    }
    return index === 0
      ? `slide-left-in ${animationSecends}s`
      : `slide-right-in ${animationSecends}s`;
  }, [isFlip, index,animationSecends]);
  return (
    <div
      className={`${clsName} ${className}`}
      style={{
        animation: animation,
        ...style,
      }}
    >
      {children}
    </div>
  );
};
CarouselItem.displayName = "CarouselItem";
export default CarouselItem;
