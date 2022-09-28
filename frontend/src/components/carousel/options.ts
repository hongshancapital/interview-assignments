import { ReactNode } from "react";

/* 配置类型 */
export interface Options {
  children: Array<ReactNode> | ReactNode;
  style?: object;
  width?: number; //宽度，不设置则为屏幕宽度
  height?: number; //高度，不设置则为300px
  loop?: boolean; //是否是循环列表，默认true
  indicator?: boolean; //是否有指示器，默认true
  controls?: boolean; //是否有左右按钮，默认true
  autoplay?: boolean; //是否自动播放，默认true
  duration?: number; //每一帧停顿时间，基于autoplay为true的时候，默认3000ms
}
