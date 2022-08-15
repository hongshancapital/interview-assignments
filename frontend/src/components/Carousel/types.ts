// data数据结构
export interface CarouseOption {
  title?: string | React.ReactElement,
  subTitle?: string | React.ReactElement,
  url: string,
  color: string
}

// Carousel组件对外提供的参数
export interface CarouselProps {
  duration: number,
  data: Array<CarouseOption>
}