export interface PropsType {
  className?: React.ClassAttributes;
  style?: React.StyleHTMLAttributes;

  /**
   * show default indicator
   * @default true
   */
  showIndicator?: boolean
  
  /**
   * auto slide
   * @default false
   */
  autoplay?: boolean;
  
  /**
   * auto slide duration
   * @unit ms
   * @default 3000
   */
  duration?: number;
  
  /**
   * slide transition speed
   * @unit ms
   * @default 500
   */
  speed?: number

  /**
   * slide transition timing function
   * @enum 'linear' | 'ease' | 'ease-in'.. reference to css property 'transition-timing-function'
   * @default 'ease'
   */
  timingFunction?: string;

  children?: React.ReactNode;

  /**
   * customize indicator
   * @param currentIndex current active item index
   * @param itemCount item total count
   * @returns ReactNode
   * @default () => void
   */
  indicatorRender?: (currentIndex: number, itemCount: number) => React.ReactNode
}

export interface RefType {
  /**
   * slide to next item
   * @returns void
   */
  next: () => void;
  /**
   * slide to prev item
   * @returns void
   */
  prev: () => void;
  /**
   * slide to item given by index
   * @returns void
   */
  goTo: (index: number) => void
}