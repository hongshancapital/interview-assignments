import { CSSProperties } from "react"

type TTransitionClallback = (startIndex: number, endIndex: number) => void

export type TCarouselProps = {
  /** 自动播放 默认false */
  autoPlay?: boolean;
  /** 自动播放建个 默认2000毫秒 */
  duration?: number;
  /** 动画执行开始 */
  onTransitionStart?: TTransitionClallback
  /** 动画执行完毕 */
  onTransitionEnd?: TTransitionClallback
}

export type TCarouselFun = {
  /** 跳转 */
  jump: (targetIndex: number) => void
  /** 开始 */
  start: () => void
  /** 暂停 */
  parse: () => void
}

export type TCarouselItemProps = {
  style?: CSSProperties;
}