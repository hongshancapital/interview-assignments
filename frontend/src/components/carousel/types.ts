export type CarouselConfigs = {
  autoplaySpeed?: number
  transitionDuration?: number
  carouselStep?: number
  onClickDot?: (index: number) => void
  autoplay?: boolean
  width?: number | string
}
