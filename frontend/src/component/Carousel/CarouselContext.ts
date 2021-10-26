import {createContext} from 'react'
import { TransitionState } from '../../hook/useCSSTransition'
import { Emitter } from '../../util/Emitter'

export type CarouselContextOptions = {
  wait : number,
  duration : number,
  size: number,
  dir : "left" | "right" 
}
export class CarouselContextInst extends Emitter<TransitionState> {
  constructor(public options : CarouselContextOptions){
    super()
  }
} 
export const CarouselContext = createContext<
  CarouselContextInst | null
>(null)