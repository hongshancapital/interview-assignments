import React, { CSSProperties, useEffect, useState } from "react";
import CarouselItem from "./CarouselItem";
import Dots from "./Dots";
import "./index.scss";
// import { useInterval } from "../../hooks";

export interface CarouselProps {
  width?: number | string;
  height?: number | string;
  autoplay?: boolean;
  interval?: number;
  transitionTime?: number; // 动画过渡时间控制
  trigger?: string;
  mouseEnterEvent?: boolean; // 是否触发鼠标经过事件，非全屏展示场景下需要
  mouseLeaveEvent?: boolean; // 是否触发鼠标离开事件，非全屏展示场景下需要
}

const DefaultProps: Required<CarouselProps> = {
  width: "100%",
  height: "100%",
  interval: 3000,
  transitionTime: 1000,
  autoplay: true,
  trigger: "hover",
  mouseEnterEvent: false, // 全屏模式下不设置此事件
  mouseLeaveEvent: false,
};

// 定时器
let timer: any = null;

const Carousel: React.FC<any> = (props) => {
  const propsConfig = { ...DefaultProps, ...props };
  const {
    interval,
    autoplay,
    children,
    width,
    height,
    mouseLeaveEvent,
    mouseEnterEvent,
  } = propsConfig;
  // 当前轮播元素的索引
  const [activeIndex, setActiveIndex] = useState<number>(0);
  // 轮播项目数量
  const carouselItemLength = children.length;

  // 切换到指定索引位置
  // const setActiveItem = (index: number): void => {
  //   // 参数校验警告提示
  //   if (isNaN(index) || index !== Math.floor(index)) {
  //     process.env.NODE_ENV !== "production" &&
  //       console.warn("[Element Warn][Carousel]index must be an integer.");
  //     return;
  //   }

  //   const length = itemList.length;
  //   // 创建新的选中元素索引
  //   let newActiveIndex: number;

  //   if (index < 0) {
  //     newActiveIndex = length - 1;
  //   } else if (index >= length) {
  //     newActiveIndex = 0;
  //   } else {
  //     newActiveIndex = index;
  //   }
  //   setActiveIndex(newActiveIndex);
  // };

  // 创建播放定时器
  const createTimer = (): void => {
    if (interval < 0 || !autoplay) return;
    clearTimer();
    timer = setInterval(() => {
      console.log("playSlide", timer);
      // 使用useState的updater句法解决无法获取最新state问题
      setActiveIndex((preIndex) => autoPlayCarousel(preIndex));
    }, interval);
  };
  // 清除定时器
  const clearTimer = (): void => {
    if (timer) {
      clearInterval(timer);
      timer = null;
    }
  };
  // 播放图片
  const autoPlayCarousel = (activeIndex: number): number => {
    let newActiveIndex: number;
    if (activeIndex < carouselItemLength - 1) {
      newActiveIndex = activeIndex + 1;
    } else {
      newActiveIndex = 0;
    }
    return newActiveIndex;
  };
  // 鼠标进入事件
  const handleMouseEnter = (): void => {
    clearTimer();
  };
  // 鼠标离开事件
  const handleMouseLeave = (): void => {
    createTimer();
  };

  useEffect(() => {
    console.log("useEff");
    createTimer();
    // 卸载组件时释放资源
    return () => {
      console.log("willUnmout");
      clearTimer();
    };
  }, []);

  // 设置Carousel样式
  const setCarouselStyle = (): CSSProperties => {
    return {
      width,
      height,
    };
  };

  return (
    <div
      className="carousel-wrap"
      onMouseEnter={() => mouseEnterEvent && handleMouseEnter()}
      onMouseLeave={() => mouseLeaveEvent && handleMouseLeave()}
      style={setCarouselStyle()}
    >
      <CarouselItem
        {...propsConfig}
        count={carouselItemLength}
        activeIndex={activeIndex}
      ></CarouselItem>
      <Dots
        {...propsConfig}
        count={carouselItemLength}
        activeIndex={activeIndex}
        setActiveIndex={setActiveIndex}
      ></Dots>
    </div>
  );
};

export default Carousel;
