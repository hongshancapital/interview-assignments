export interface Image {
  src?: string,
  text?: string
}

export interface Props {
  list: Array<CarouselItemProps>,
  height?: number,
  width: number
}

export interface CarouselItemProps {
  src?: string,
  title?: string,
  text?: string,
  color?: string,
  background?: string,
}

export interface CarouselProps {
  duration?: number,
  width?: number,
  height?: number,
  carouselList: CarouselItemProps[]
}