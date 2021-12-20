// App image data interface
export interface IImageData {
  url?: string;
  title?: string;
  titleColor?: string,
  text?: string,
  textColor?: string 
}
// App attribute interface
export interface IAttribute {
  interval: number,
  slideTransitionClassName: string,
  progressBarClassName: string,
  barClickChangeImg: boolean
}
// Carousel props interface
export interface IProps {
  data: any;
  attr: any;
}