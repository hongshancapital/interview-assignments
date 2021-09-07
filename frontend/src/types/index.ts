import { ReactNode, CSSProperties } from "react";

export type SlideType = {
  id: number,
  title: string,
  text: string,
  description: string,
  img: string
}

export type ProgressBarType = {
  isCurrent: boolean,
  time: number,
  key?: string,
  style?: CSSProperties
}

export type CarouselType = {
  delay: number,
  slides: SlideType[],
  renderNavItem?: (navProps: ProgressBarType) => ReactNode,
  style?: CSSProperties
}