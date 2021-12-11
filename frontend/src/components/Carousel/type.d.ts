interface BaseCarouselProps {
  /** 是否显示导航控制 */
  showNavi: boolean;
  /** 是否显示进度指示器 */
  showIndicator: boolean;
  /** 鼠标滑过时是否暂停轮播 */
  overPause: boolean;
  /** 动画间隔时间 ms */
  duration: number;
}

export interface CarouselContainerProps {
  /** Carousel container width */
  width?: string;
  /** Carousel container height */
  height?: string;
  /** container dom ref forward to parent */
  ref?: React.Ref<HTMLElement>;
}

export interface CarouselNaviProps {
  onPre?: () => void | undefined;
  onNext?: () => void | undefined;
}

export interface CarouselTransitionProps {
  animation?: 'slideLeft' | 'slideRight'; // TODO | 'slideUp' | 'slideDown';
  step: number;
}

export interface CarouselIndicatorProps {
  step: number;
  count: number;
  duration: number;
  onClick?: (index: number) => void;
}

export type CarouselProps = Partial<
  BaseCarouselProps & CarouselNaviProps & CarouselContainerProps & CarouselTransitionProps
>;
