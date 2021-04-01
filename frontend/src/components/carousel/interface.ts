
type carouselData = {
  img: string
  font: font[]
}

type font = {
  text:string
  color: string
  fontSize:string
}

export interface propsType {
  carouselList: carouselData[]
  speed?: number
}