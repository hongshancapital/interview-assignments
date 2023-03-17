import { CSSProperties, Key } from "react";

export interface SlideProps {
  //ID
  id: Key;
  // 标题
  title?: string[];
  // 描述
  description?: string[];
  // 图标
  icon: string;
  // 背景色
  backgroundColor: CSSProperties["backgroundColor"];
  // 文字颜色
  textColor?: CSSProperties["color"];
}

export interface IndicatorsProps
  extends Pick<CarouselConfig, "delay" | "autoplay"> {
  // 数据
  data: SlideProps[];
  // 当前所在的轮播图ID
  activeSlideId: Key;
  // 点击事件
  onClick: (id: Key) => void;
}

export interface IndicatorProps
  extends Pick<CarouselConfig, "delay" | "autoplay"> {
  // 单个指示器的ID
  id: Key;
  // 指示器是否处于活跃状态
  isActive: boolean;
  // 点击事件
  onClick: (id: Key) => void;
}

export interface CarouselConfig {
  // 每次轮播图的切换时间，默认3000（单位毫秒）
  delay?: number;
  // 是否自动播放，默认为true
  autoplay?: boolean;
  // 走马灯的宽度, 默认为600px
  width?: CSSProperties["width"];
  // 走马灯的高度，默认为400px
  height?: CSSProperties["height"];
}

export interface CarouselProps extends CarouselConfig {
  // 走马灯的数据
  data: SlideProps[];
}
