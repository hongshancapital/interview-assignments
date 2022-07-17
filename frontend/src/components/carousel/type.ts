import React, {ReactNode} from 'react'

export interface CarouselProps {
  className?: string
  style?: React.CSSProperties
  /**
   * 默认激活索引(从0开始计算索引)
   * @default 0
   */
  defaultActive?: number
  /**
   * 是否展示指示器
   * @default true
   */
  showIndicator?: boolean
  /**
   * 自动切换间隔(0代表不用自动切换)
   * @default 0
   */
  duration?: number

  children?: ReactNode | undefined
}