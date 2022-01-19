import { CSSProperties } from "react";

export interface ICarouselItemProps {
  title: string[]
  content?: string[]
  image: string
  color?: string
  bgColor: string
}

export interface ICarouselProps {
  items: ICarouselItemProps[]
  current?: number
  duration?: number
  transitionDuration?: number
  style?: CSSProperties
  className?: string
}