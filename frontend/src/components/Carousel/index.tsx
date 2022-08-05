import React, { useRef, useEffect, useLayoutEffect } from "react"
import Poster from "../Poster"
import Progress from "../Progress"
import { useCarousel, useCarouselDispatch } from "../../store/AppContext"
import { ECarouselActionType } from "../../store/types"
import "./styles.css"
const START_POINT = 0
const DISTANCE = 100

export default function Carousel() {
  const { posters, move, progressId, lastId } = useCarousel()
  const dispatch = useCarouselDispatch()
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
        { duration: 500, fill: 'forwards' }
      )
      const anim = new Animation(keyframes, document.timeline)
      anim.play()
      const finish = anim.finished
      finish.then(() => {
          dispatch({
            type: ECarouselActionType.SET_CURRENT,
          })
      })
    }
  },[move])
  return (
    <div className="carousel">
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