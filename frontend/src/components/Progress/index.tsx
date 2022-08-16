import React, { useRef, useState, useLayoutEffect, useEffect } from "react"
import { useCarousel, useCarouselDispatch } from "../../stores/CarouselContext"
import { ECarouselActionType, TCarouselAction } from "../../stores/types"
import "./styles.css"

export default function Progress( { pid, time=3000 }: { pid: number, time?: number}) {
  const { progressId } = useCarousel()
  let dispatch:React.Dispatch<TCarouselAction> | null  = useCarouselDispatch() 
  const inner = useRef<HTMLDivElement>(null)
  useLayoutEffect(()=>{
    if (pid === progressId) {
      const keyframes = new KeyframeEffect(
          inner.current, 
          [
            { transform: 'scaleX(0%)', transformOrigin: 'left center' }, 
            { transform: 'scaleX(100%)', transformOrigin: 'left center' }
          ],
          { duration: time, fill: 'forwards' }
        );
        const anim = new Animation(keyframes);
        anim.play()
        const finish = anim.finished
        finish.then(() => {
          if (dispatch) {
            dispatch({
              type: ECarouselActionType.SET_MOVE
            })
          }
        })
    }
    return () => {
      dispatch = null
    }
  }, [progressId])
  return (
    <div className="Progress">
      <div className="progress-wrap">
        <div className="progress-bar progress-outer">
          <div 
            className={`progress-bar progress-inner ${pid === progressId ? 'progress-active' : 'progress-inactive'}`} 
            ref={inner}
          >
          </div>
        </div>
      </div>
    </div>
  )
}