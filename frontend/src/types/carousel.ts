import { ReactElement } from "react";

export interface CarouselProps {
  children: JSX.Element[]; // 轮播列表
  className?: string;
  duration?: number; // 轮播滑动动画时间
  delay?: number; // 轮播滑动间隔时间
  autoPlay?: boolean; // 是否自动轮播
  useArrowKeys?: boolean; // 是否使用键盘控制左右滑动
}

export interface CarouselItemProps {
  children?: ReactElement | ReactElement[];
  infinite?: boolean;
  className?: string;
  currentStateIndex: number;
  duration: number; // 轮播滑动动画时间
}

export interface CarouselDotsProps {
  count: number;
  currentStateIndex: number;
  infinite?: boolean;
  delay: number;
  onSlideToIndex: (index: number) => void;
}

export interface CarouselArrowProps {
  showArrow: boolean;
  onSlideToDirection: (direction: SlideDirection) => void;
}

export enum SlideDirection {
  Left = -1,
  Right = 1,
}

export enum ArrowKeys {
	Right = 39,
	Left = 37,
}