export interface ICarouselData {
  autoPlay: boolean
  progressId: number
  posters: IPosterData[]
}


export interface IPosterData {
  posterId: number
  bgColor: string
  pic: IPic
  contents: IDescData[]
}

export interface IProgressData {
  width: number
  height: number
}

export interface IDescData {
  text: string
  style: IDescStyle
}

export interface IDescStyle {
  color: string
  fontSize: string
  paddingTop?: string
  paddingBottom?: string
}

export interface IPic {
  name: string
  width: string
  height: string
}

