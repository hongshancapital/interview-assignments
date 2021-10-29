import { useState } from "react"
import { useCSSTransition, TransitionState } from "../../hook/useCSSTransition"

/**
 * 向左滑动一个单位 
 * @param v 
 * @param size 
 * @returns 
 */
export function prev(v : number, size: number) {
  return Math.min(v === 0 ? size - 1 : v - 1, size)
}

/**
 * 向右滑动一个单位 
 * @param v 
 * @param size 
 * @returns 
 */
export function next(v : number, size: number) {
  return Math.min(v === size- 1 ? 0 : v + 1, size)
}


export function calcTransition(x : number, size : number, duration : number){
  const preVal = -x * 100 
  const postVal = -next(x, size)*100

  return {
    prepare : {
      transform: `translateX(${preVal}%)`
    },
    enter : {
      transform: `translateX(${postVal}%)`,
      transition: `transform ${duration/1000}s ease`
    }, 
    leave : {
      transform: `translateX(${postVal}%)`,
    }
  }
}
export function useCarousel(size : number, duration : number, wait : number){

  const [x, setX] = useState(0)

  function slideNext(){
    setX(x => next(x, size))
  }

  const transitions = calcTransition(x, size, duration)
  const [state, scrollerStyle] = useCSSTransition({
    transitions,
    duration,
    wait,
    initialStyle : transitions.enter,
    loop : true,
    on(topic) {
      if(topic === TransitionState.FINISH) {
        slideNext()
      }
    }
  })

  return {scrollerStyle: scrollerStyle , position : x, state}
}