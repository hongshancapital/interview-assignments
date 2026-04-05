/*
 * @Description: 轮播组件
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-07 18:11:39
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-12 22:56:40
 */

import React, { useRef, useState } from "react";
import { getValidChildNodes } from "../../utils";
import ProcessBar from "./ProcessBar";
import "./index.css";
import { CarouselProps } from "./types";
import useInterval from "../../hooks/useInterval";
import CarouselItem from "./CarouselItem";
import CarouselContext from './CarouselContext'
const Carousel = (props: CarouselProps) => {
  const {
    children,
    style,
    className = "",
    onChange,
    duration = 2,
    animationSecends = 0.8,
  } = props;
  const [currentIndex, setCurrentIndex] = useState(0);
  const isFlipRef = useRef(false);
  const validChildNodes = getValidChildNodes(children, "CarouselItem");
  const childLen = validChildNodes.length;
  useInterval(
    () => {
      setCurrentIndex((prev) => {
        const curIndex = prev < childLen - 1 ? prev + 1 : 0;
        onChange && onChange(curIndex);
        isFlipRef.current = true;
        return curIndex;
      });
    },
    duration,
    false
  );
  return (
    <CarouselContext.Provider value={{
      current: currentIndex,
      isFlip: isFlipRef.current,
      itemCount: validChildNodes.length,
      animationSecends
    }}>
      <div className={`carousel ${className}`} style={style}>
        {validChildNodes}
        <div className="process">
          <ProcessBar />
        </div>
      </div>
    </CarouselContext.Provider>
  );
};
Carousel.displayName = "Carousel";
Carousel.CarouselItem = CarouselItem;
export default Carousel;
