import React, { FC, ReactElement, ReactNode, useEffect, useState } from "react";
import "./index.css";

/**
 * Carousel 走马灯组件
 * 这些可以作为后续拓展，在Carousel组件和相关样式上设计即可
 * 此组件未做大小拓展，默认铺满整个屏幕，若需要拓展后续将参数传入即可
 * autoplay
 * dotPosition
 * dots
 * easing
 * effect
 * afterChange
 * beforeChange
 * **/

/**
 * 整体思路:
 * Carousel负责外层布局和dots
 * CarouselItem 负责动画层渲染
 * TODO:可拓展 CarouselInfo,自定义内容样式
 * **/

/**
 * ex:
 *  <Carousel>
 *   <CarouselItem>
 *      some dom
 *   </CarouselItem>
 *  </Carousel>
 *
 * **/


type CarouselItemProps = {
  autoPlay?: boolean;
  dotPosition?: string;
  dots?: boolean;
  styles?: object;
};

type Props = {
  width: string;
  height: string;
  duringTime?: number;
  children?: JSX.Element | JSX.Element[];
};

export const CarouselItem: FC<CarouselItemProps> = ({
  children = React.createElement("div"),
  styles = {},
}) => {
  return (
    <div
      className="carousel_item"
      style={{
        width: "100%",
        height: "100vh",
        ...styles,
      }}
    >
      {children}
    </div>
  );
};

// Carousel 组件
const Carousel: FC<Props> = ({
  children = React.createElement("div"),
  duringTime = 3000,
  width,
  height,
}) => {
  // 处理时间
  const time: string = (duringTime / 1000).toFixed(0);
  // 默认索引
  const [activeIndex, setActiveIndex] = useState(0);

  // 重置动画
  const replayAnimations = () => {
    document.getAnimations().forEach((item) => {
      item.cancel();
      item.play();
    });
  };

  // 更新索引
  const onUpdateIndex = (newIndex: number) => {
    if (newIndex < 0) {
      newIndex = React.Children.count(children) - 1;
    } else if (newIndex >= React.Children.count(children)) {
      newIndex = 0;
    }
    setActiveIndex(newIndex);
    // 重置动画
    replayAnimations();
  };

  // 底部按钮的点击事件
  const onBottomPress = (index: number) => {
    onUpdateIndex(index);
    replayAnimations();
  };

  useEffect(() => {
    // 开启自动定时切换
    const inteval = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, duringTime);

    // 清楚定时器
    return () => {
      if (inteval) {
        clearInterval(inteval);
      }
    };
  });

  return (
    <div
      className={"carousel_container"}
      style={{ width: width, height: height }}
    >
      <div
        className="carousel_content"
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {/* 渲染CarouselItem */}
        {React.Children.map(children, (child:ReactElement) => {
          return React.cloneElement(child, { width: "100%", height: "100%" });
        })}
      </div>
      {/* 小点点 */}
      <div className="carousel_dots">
        {React.Children.map(children, (child: any, index: number) => {
          // 进度条效果
          return (
            // 外面
            <div
              className="dots_outer"
              onClick={() => {
                onBottomPress(index);
              }}
            >
              <div
                className="dots_inner"
                style={{
                  animationDuration: index === activeIndex ? `${time}s` : `0s`,
                  background: index === activeIndex ? `#fff` : "#0000001a",
                }}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
