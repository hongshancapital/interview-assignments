// data数据结构
export interface Options {
  title?: string | React.ReactElement,
  subTitle?: string | React.ReactElement,
  url: string,
  color: string
}

// Carousel组件对外提供的参数
export interface CarouselProps {
  delay?: number,
  duration?: number,
  data: Array<Options>
}