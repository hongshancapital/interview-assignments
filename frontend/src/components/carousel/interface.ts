export type DotPosition = 'top' | 'right' | 'bottom' | 'left'

export interface CarouselProps {
  /**
   * 是否自动播放
   * @default true
   */
  autoplay?: boolean
  /**
   * 默认展示第几项
   * @default 0
   */
  defaultIndex?: number
  /**
   * 轮播间隔, 为 0 时，不轮播
   * @default 3000
   * @unit ms
   */
  duration?: number
  /**
   * 是否显示面板指示点,也可以是对象为知识点添加类名
   * @default true
   */
  dots?: boolean | { className: string }
  /**
   * 面板指示点位置
   * @default 'bottom'
   */
  dotPosition?: DotPosition
  /**
   * 类名信息
   */
  className?: string
  /**
   * 插槽内容
   */
  children?: React.ReactNode;
  /**
   * 切换面板前的回调，可以通过返回 `false` 停止跳转
   * @param from 起点面板
   * @param to 终点面板
   */
  beforeChange?: (from: number, to: number) => boolean | void
  /**
   * 切换面板后的回调
   * @param current 当前所在面板
   */
  afterChange?: (current: number) => void
}

export interface CarouselRef {
  /**
   * 指定前往第几页
   * @param slide 轮播页码
   * @param animation 是否使用动画，默认值 `true`
   */
  goTo: (slide: number, animation?: boolean) => void;
  /**
   * 下一页
   */
  next: () => void;
  /**
   * 上一页
   */
  prev: () => void;
}

/**
 * 当前执行动画状态
 */
export type AnimationState = 'paused' | 'running'
