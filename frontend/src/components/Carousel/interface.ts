import { ReactNode } from "react";

type ICarouselConfigType = {
  /** 代表是否到左边界 */
  isLeftBorder: boolean;
  /** 代表是否到右边界 */
  isRightBorder: boolean;

  /** 当前的展示图片的位置 */
  currentIndex: number;

  /** 基础偏移量 */
  baseOffset: number;
};

type ICarouselProps = {
  /** 切换时候的回调事件 */
  onChange?(idx: number): void;
  /** 默认banner的切换时间 */
  time?: number;
  /** 是否自动播放 */
  autoplay?: boolean;
  children: ReactNode;
};

export type { ICarouselConfigType, ICarouselProps };
