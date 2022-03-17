export interface ICarouselProps {
  /**
   * one page dwell time, unit of ms
   * default: 5000ms
  */
  duration?: number
  /**
   * carousel component children
   */
  children: any
}

export interface IDotProps {
  /**
   * one page dwell time, unit of ms
   * default: 5000ms
  */
  duration: number
  /**
   * current page index
  */
  currIndex: number
  /**
   * children count
  */
  count: number
}

export interface IDotItemProps {
  /**
   * one page dwell time, unit of ms
   * default: 5000ms
  */
  duration: number
  isActive: boolean
}