export interface iItemProps {
  src: string
  titles?: string[]
  subTexts?: string[]
  textColor?: string
}

export interface CarouselProps {
  carouselItems: iItemProps[]
  speed: number
}

export interface cb {
  (msTime: number): void
}