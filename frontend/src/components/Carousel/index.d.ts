export interface PropsType {
  className?: React.ClassAttributes;
  style?: React.StyleHTMLAttributes;
  children?: React.ReactElement | Array<React.ReactElement>;

  /**
   * @description show default indicator
   * @default true
   */
  showIndicator?: boolean;
  
  /**
   * @description autoplay
   * @default false
   */
  autoplay?: boolean;
  
  /**
   * @description autoplay duration
   * @unit ms
   * @default 3000
   */
  duration?: number;
  
  /**
   * @description slide transition speed
   * @unit ms
   * @default 500
   */
  speed?: number;

  /**
   * @description slide transition timing function
   * @enum 'linear' | 'ease' | 'ease-in'.. reference to css property 'transition-timing-function'
   * @default 'ease'
   */
  timingFunction?: string;

  /**
   * @description active item change handler
   * @param currentIndex current active item index
   * @param prevIndex previous active item index
   * @returns void
   * @default () => void
   */
  onChange?: (currentIndex: number, prevIndex: number) => void;

  /**
   * @description customize indicator render function
   * @param currentIndex current active item index
   * @param itemCount item total count
   * @returns ReactNode
   * @default () => void
   */
  indicatorRender?: (currentIndex: number, itemCount: number) => React.ReactNode;
}

export interface RefType {
  /**
   * @description change to next item
   * @returns void
   */
  next: () => void;
  /**
   * @description change to previous item
   * @returns void
   */
  prev: () => void;
  /**
   * @description change to item given by index
   * @returns void
   */
  goTo: (index: number) => void;
}