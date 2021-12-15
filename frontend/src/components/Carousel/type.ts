export interface ICarouselConfigType {
  id: string | number,
  img: string,
  title: string[] | string,
  subTitle?: string[] | string,
  color?: string,
  backgroundColor?: string
}

export interface ICarouselPropsType {
  config: ICarouselConfigType[],
  delay?: number,
  auto?: boolean,
  onChange?: (index: number) => void
}

export interface ICarouselViewType extends ICarouselConfigType {
  imgStyle: React.CSSProperties,
  title: string[],
  subTitle: string[]
}

export interface ICarouselRefType {
  [key: number]: HTMLDivElement | null;
}
