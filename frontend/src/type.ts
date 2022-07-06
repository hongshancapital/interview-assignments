export type carousel = {
  title: string
  textDes?: string
  imgSrc: string
  bgColor: string
  fontColor: string
  id: string
}
export type dotProps = {
  currIndex: number
  countdown: number
  index: number
  tabChange: (para: number) => void
  startAnimation: () => void
  stopAnimation: () => void
}
export type carouselProps = {
  speed?: number
  carouselList: carousel[]
}
