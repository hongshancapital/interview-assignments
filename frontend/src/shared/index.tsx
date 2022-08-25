export interface CarouselData{
    key: string,
    img: string,
    title: string,
    subtitle?: string,
    color?: string,
}

export interface CarouselInter{
  items:Array<CarouselData>
}