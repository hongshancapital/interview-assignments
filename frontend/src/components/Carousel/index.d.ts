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
}