import React, { useRef, useEffect, useLayoutEffect } from "react"
import Poster from "../Poster"
import Progress from "../Progress"
import { useCarousel, useCarouselDispatch } from "../../stores/CarouselContext"
import { ECarouselActionType, TCarouselAction } from "../../stores/types"
import "./styles.css"
const START_POINT = 0
const DISTANCE = 100

export default function Carousel() {
  const { posters, move, progressId, lastId } = useCarousel()
  let dispatch:React.Dispatch<TCarouselAction> | null  = useCarouselDispatch() 
  const track = useRef<HTMLDivElement>(null)
  // const catchRect = useRef<DOMRect | undefined>(undefined)
  useLayoutEffect(()=>{
    // catchRect.current =  track?.current?.getBoundingClientRect()
    if (move) {
      let distance = 0
      if (progressId < lastId) {
        distance = START_POINT - (progressId + 1) * DISTANCE
      }
      const keyframes = new KeyframeEffect(   
        track.current,          
        [
          { transform: `translateX(${distance}vw)`, transformOrigin: 'left center' }
        ],
        { duration: 400, fill: 'forwards' }
      )
      const anim = new Animation(keyframes)
      anim.play()
      let finish = anim.finished
      finish.then(() => {
        if (dispatch) {
          dispatch({
            type: ECarouselActionType.SET_CURRENT,
          })
        }
      })
    }
    return () => {
      dispatch = null
    }
  },[move])
  return (
    <div className="Carousel">
      <div className="carousel-wrap">
        <div className="carousel-track" ref={track}>
          {
            posters.map(({ posterId} ) => {
              return (
                <Poster key={posterId} pid={posterId} />
              )
            })
          }
        </div>
      </div>
      <div className="progress-row">
        {
          posters.map(({ posterId} ) => {
            return (
              <Progress key={`progress${posterId}`} pid={posterId} />
            )
          })
        }
      </div>
    </div>
  )  
}