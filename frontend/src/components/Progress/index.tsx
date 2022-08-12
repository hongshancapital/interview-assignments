import React, { useRef, useState, useLayoutEffect, useEffect } from "react"
import { useCarousel, useCarouselDispatch } from "../../store/AppContext"
import { ECarouselActionType, TCarouselAction } from "../../store/types"
import "./styles.css"

export default function Progress( { pid }: { pid: number}) {
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
          { duration: 3000, fill: 'forwards' }
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
            className="progress-bar progress-inner" 
            ref={inner} 
            style={{ background: pid === progressId ? '#fff' : '#9aa0a6'}}
          >
          </div>
        </div>
      </div>
    </div>
  )
}