import { ReactNode, CSSProperties, MutableRefObject } from 'react';

/* eslint-disable no-shadow */

export enum Alignment {
  Center = 'center',
  Right = 'right',
  Left = 'left'
}

export enum Directions {
  Next = 'next',
  Prev = 'prev',
  Up = 'up',
  Down = 'down'
}

export enum Positions {
  TopLeft = 'TopLeft',
  TopCenter = 'TopCenter',
  TopRight = 'TopRight',
  CenterLeft = 'CenterLeft',
  CenterCenter = 'CenterCenter',
  CenterRight = 'CenterRight',
  BottomLeft = 'BottomLeft',
  BottomCenter = 'BottomCenter',
  BottomRight = 'BottomRight'
}

type SlideChildren = {
  offsetHeight: number;
};

export type SlideHeight = {
  height: number;
  slideIndex: number;
};

export interface Slide {
  children?: [SlideChildren];
  offsetHeight: number;
}

export enum D3EasingFunctions {
  EaseLinear = 'easeLinear',
  EaseQuad = 'easeQuad',
  EaseQuadIn = 'easeQuadIn',
  EaseQuadOut = 'easeQuadOut',
  EaseQuadInOut = 'easeQuadInOut',
  EaseCubic = 'easeCubic',
  EaseCubicIn = 'easeCubicIn',
  EaseCubicOut = 'easeCubicOut',
  EaseCubicInOut = 'easeCubicInOut',
  EasePoly = 'easePoly',
  EasePolyIn = 'easePolyIn',
  EasePolyOut = 'easePolyOut',
  EasePolyInOut = 'easePolyInOut',
  EaseSin = 'easeSin',
  EaseSinIn = 'easeSinIn',
  EaseSinOut = 'easeSinOut',
  EaseSinInOut = 'easeSinInOut',
  EaseExp = 'easeExp',
  EaseExpIn = 'easeExpIn',
  EaseExpOut = 'easeExpOut',
  EaseExpInOut = 'easeExpInOut',
  EaseCircle = 'easeCircle',
  EaseCircleIn = 'easeCircleIn',
  EaseCircleOut = 'easeCircleOut',
  EaseCircleInOut = 'easeCircleInOut',
  EaseBack = 'easeBack',
  EaseBackIn = 'easeBackIn',
  EaseBackOut = 'easeBackOut',
  EaseBackInOut = 'easeBackInOut',
  EaseBounce = 'easeBounce',
  EaseBounceIn = 'easeBounceIn',
  EaseBounceOut = 'easeBounceOut',
  EaseBounceInOut = 'easeBounceInOut',
  EaseElastic = 'easeElastic',
  EaseElasticIn = 'easeElasticIn',
  EaseElasticOut = 'easeElasticOut',
  EaseElasticInOut = 'easeElasticInOut'
}

interface DefaultControlsConfig {
  containerClassName?: string;
  nextButtonClassName?: string;
  nextButtonStyle?: CSSProperties;
  nextButtonText?: string;
  prevButtonClassName?: string;
  prevButtonStyle?: CSSProperties;
  prevButtonText?: string;
}

export interface KeyCodeConfig {
  firstSlide?: number[];
  lastSlide?: number[];
  nextSlide?: number[];
  pause?: number[];
  previousSlide?: number[];
}

export type KeyCodeFunction =
  | 'nextSlide'
  | 'previousSlide'
  | 'firstSlide'
  | 'lastSlide'
  | 'pause'
  | null;

export interface KeyCodeMap {
  [key: number]: keyof KeyCodeConfig;
}

export interface CarouselState {
  cellAlign: Alignment;
  /**
   * current index
   */
  currentSlide: number;
  dragging: boolean;
  easing: (normalizedTime: number) => number;
  hasFocus: boolean;
  hasInteraction: boolean;
  resetWrapAroundPosition: boolean;
  count: number;
  slidesToScroll: number;
  slidesToShow: number;
  left: number;
  top: number;
  wrapToIndex: number | null;
}

export interface ControlProps {
  /**
   * When displaying more than one slide, sets which position to anchor the current slide to.
   */
  cellAlign: Alignment;

  /**
   * Space between slides, as an integer, but reflected as px
   */
  cellSpacing: number;

  /**
   * Current slide index
   */
  currentSlide: number;

  /**
   * default Control config
   */
  defaultControlsConfig: DefaultControlsConfig;

  /**
   * Go to X slide method
   * @param index Slide's index to go
   */
  goToSlide: (index: number) => void;

  /**
   * Go to the next slide method
   */
  nextSlide: () => void;

  /**
   * Go to the previous slide method
   */
  previousSlide: () => void;

  /**
   * Total amount of slides
   */
  slideCount: number;

  /**
   * 每次滚动 slide 的个数
   */
  slidesToScroll: number;

  /**
   * 控制当前 slide 显示个数
   */
  slidesToShow: number;

  /**
   * Enable the slides to transition vertically
   */
  vertical: boolean;
}

export type RenderControlFunctionNames =
  | 'renderTopLeftControls'
  | 'renderTopCenterControls'
  | 'renderTopRightControls'
  | 'renderCenterLeftControls'
  | 'renderCenterCenterControls'
  | 'renderCenterRightControls'
  | 'renderBottomLeftControls'
  | 'renderBottomCenterControls'
  | 'renderBottomRightControls';

/**
 * render controls
 */
type RenderControls = ((props: ControlProps) => ReactNode) | null;

export interface InternalCarouselProps {
  /**
   * If it's set to true, the carousel will adapt its height to the visible slides.
   */
  adaptiveHeight: boolean;

  /**
   * Hook to be called after a slide is changed
   * @param index Index of the current slide
   */
  afterSlide: (index: number) => void;

  /**
   * 支持 缩放｜淡入淡出
   * Support Animation 'zoom' or 'fade' Mode
   */
  animation?: 'zoom' | 'fade';

  /**
   * Autoplay mode active
   * @default false
   */
  autoplay: boolean;

  /**
   * 自动执行事件间隔 (ms)
   * @default 3000
   */
  autoplayInterval: number;

  /**
   * 是否支持反复循环播放
   * @default false
   */
  autoplayReverse: boolean;

  /**
   * Hook to be called before a slide is changed
   * @param currentSlide Index of the current slide
   * @param endSlide Index of the last slide
   */
  beforeSlide: (currentSlideIndex: number, endSlideIndex: number) => void;

  /**
   * When displaying more than one slide,
   * sets which position to anchor the current slide to
   */
  cellAlign: Alignment;

  /**
   * Space between slides, as an integer, but reflected as px
   */
  cellSpacing: number;

  /**
   * Explicit children prop to resolve issue with @types/react v18
   */
  children: ReactNode | ReactNode[];

  /**
   * Additional className
   */
  className?: string;

  /**
   * This prop lets you apply custom classes and styles to the default Next, Previous, and Paging Dots controls
   */
  defaultControlsConfig: DefaultControlsConfig;

  /**
   * 禁用动画
   * @default false
   */
  disableAnimation: boolean;

  /**
   * 禁用滑动 第一个 ｜ 最后一个
   * @default false
   */
  disableEdgeSwiping: boolean;

  /**
   * Not migrated yet
   *
   * Animation easing function
   * @see https://github.com/d3/d3-ease
   */
  easing: D3EasingFunctions;

  /**
   * Not migrated yet
   *
   * Animation easing function when swipe exceeds edge
   * @see https://github.com/d3/d3-ease
   */
  edgeEasing: D3EasingFunctions;

  /**
   * 自定义轮博框架容器属性 aria-label
   * 当页面有多个轮播时通过设置 frameAriaLabel 区分
   */
  frameAriaLabel?: string;

  /**
   * slider Ref
   */
  innerRef?: MutableRefObject<HTMLDivElement>;


  /**
   * Function for rendering aria-live announcement messages
   */
  // renderAnnounceSlideMessage: RenderAnnounceSlideMessage;

  /**
   * Function for rendering bottom center control
   */
  renderBottomCenterControls?: RenderControls;

  /**
   * Function for rendering bottom left control
   */
  renderBottomLeftControls?: RenderControls;

  /**
   * Function for rendering bottom right control
   */
  renderBottomRightControls?: RenderControls;

  /**
   * Function for rendering center center control
   */
  renderCenterCenterControls?: RenderControls;

  /**
   * Function for rendering center left control
   */
  renderCenterLeftControls: RenderControls;

  /**
   * Function for rendering center right control
   */
  renderCenterRightControls: RenderControls;

  /**
   * Function for rendering top center control
   */
  renderTopCenterControls?: RenderControls;

  /**
   * Function for rendering top left control
   */
  renderTopLeftControls?: RenderControls;

  /**
   * Function for rendering top right control
   */
  renderTopRightControls?: RenderControls;

  /**
   * 初始轮播索引
   */
  slideIndex: number;

  /**
   * 滑动一次滚动
   */
  slidesToScroll: number;

  /**
   * 立即显示 slide
   */
  slidesToShow: number;

  /**
   * Animation duration
   */
  speed: number;

  /**
   * style object
   */
  style: CSSProperties;

  /**
   * Enable touch swipe/dragging
   */
  swiping: boolean;

  /**
   * Enable the slides to transition vertically
   */
  vertical: boolean;

  /**
   * 删除所有控件
   * @default false
   */
  withoutControls: boolean;

  /**
   * Adds a number value to set the scale of zoom when animation === "zoom".
   * The number value should be set in a range of (0,1).
   * @default 0.85
   */
  zoomScale?: number;
}

/**
 * This component has no required props.
 */
export type CarouselProps = Partial<InternalCarouselProps>;
