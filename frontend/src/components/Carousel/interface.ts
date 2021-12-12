import React from "react";

export interface ICarouselProps {
  children: Array<React.ReactElement>,
  height: string,
  duration: number, // 过渡动画时间
  delay: number, // 等待时间
  showPagination?: boolean, // 是否显示导航指示器
  autoPlay?: boolean, // 是否自动轮播
  onSlideChange?: (index: number) => void, // 轮播变化时的回调
}


export interface IPagination {
  count: number,
  delay: number,
  active: number,
  duration: number,
  onClick?: (index: number) => void
}