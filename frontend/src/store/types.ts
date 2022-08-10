export interface ICarouselData {
  move: boolean // control whether the carousel moves(translateX)
  progressId: number // current progress id
  lastId: number // last progress id
  posters: IPosterData[]
}


export interface IPosterData {
  posterId: number
  bgColor: string
  pic: IPic
  descs: IDescData[]
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

export interface ICarouselAction {
  type: ECarouselActionType
  payload: {
      [key: string]: any
  }
}

export interface IReducerFun {
  (nitialData: ICarouselData, action: ICarouselAction): ICarouselData
}

export enum ECarouselActionType {
  SET_CURRENT = 'SET_CURRENT',
  SET_MOVE = 'SET_MOVE'
}

interface IActionSetCurrent {
  type: ECarouselActionType.SET_CURRENT
}

interface IActionSetMove {
  type: ECarouselActionType.SET_MOVE
}

export type TCarouselAction = IActionSetCurrent | IActionSetMove