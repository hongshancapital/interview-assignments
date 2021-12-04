interface CarouselData {
  datasource: Array<CarouselDataItem>,
  duration: number
}
interface CarouselDataItem {
  titles: Array<string>,
  texts: Array<string>,
  image: string,
  theme?: string
}
interface CarouselItemProps {
  data: CarouselDataItem
}
interface CarouselDotsProps {
  itemCount: number,
  duration: number,
  onIndex: number,
  onAnimationEnd: () => void
}