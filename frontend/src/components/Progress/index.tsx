import React, { useRef, useState, useLayoutEffect, useEffect } from "react"
import { useCarousel, useCarouselDispatch } from "../../store/AppContext"
import { ECarouselActionType } from '../../store/types'
import "./styles.css"

export default function Progress( { pid }: { pid: number}) {
  const { progressId } = useCarousel()
  const dispatch = useCarouselDispatch()
  const inner = useRef<HTMLDivElement>(null)
  console.log('ppp111111', pid)
  useLayoutEffect(()=>{
    console.log('ppp22222')
    if (pid === progressId) {
      console.log('ppp3333333333333333333', progressId)
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
        console.log('ppp4444')
        finish.then(() => {
          console.log('ppp55555')
          dispatch({
            type: ECarouselActionType.SET_MOVE
          })
        })
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