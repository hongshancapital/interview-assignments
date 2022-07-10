import { ReactElement } from "react";

/**
 * 跑马灯构造参数
 */
export interface CarouselProps {
  /**
   * 单页内容
   */
  content: ReactElement[];
  /**
   * 是否自动播放，默认为true
   */
  autoPlay?: boolean;
  /**
   * 切换页面持续时间，autoPlay为true时生效
   */
  delay?: number;
  /**
   * 是否开启循环，默认为true
   */
  loop?: boolean;
  /**
   * 圆点位置
   */
  dots?: "inside" | "outside" | "none";
  /**
   * 页签切换回调事件
   */
  onChange?: (index: number) => any
}
