import { ReactNode } from "react";

export interface IndicatorProps {
  // 当前索引
  activeIndex: number;
  // 间隔时间
  delay: number;
  // 数量
  length: number;
  // 点击回调
  onDotClick: (index: number) => void
}


export interface CarouselProps {
  // 自动播放
  autoPlay?: boolean;
  // 间隔时间 毫秒
  delay?: number;
  // 轮播速度 毫秒
  speed?: number;
  // 子元素
  children: ReactNode
}