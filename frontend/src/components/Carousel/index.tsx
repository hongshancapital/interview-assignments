/*
 * @Author: xzg 
 * @Date: 2022-08-20 01:40:50 
 * @Last Modified by: xzg
 * @Last Modified time: 2022-08-20 12:50:54
 */
import React, { useRef, useState, useEffect } from "react"
import Poster from "../Poster"
import Progress from "../Progress"
import "./styles.css"
import { IPosterData } from "../stores/types"

const OFFSET_BASE = 100
const DEFAULT_TIME = 3000
const IS_AUTO_PLAY = true

export type Props = {
  posters: IPosterData[],
  autoPlay: boolean,
  time: number
}
/**
 * 轮播组件
 * @param posters 轮播图片数据 {IPosterData[]}
 * @param move 是否自动轮播 {boolean}
 * @param time 轮播间隔，单位毫秒 {number}
 */
export default function Carousel({ posters, autoPlay = IS_AUTO_PLAY, time = DEFAULT_TIME }: Props) {
  const [posterIndex, setPosterIndex] = useState(0);
  const posterContainerRef = useRef<HTMLDivElement>(null)
  function setPosterTranslate(posterIndex: number) {
    let offsetLeft: number = 0
    let poserId: number = posterIndex ? posterIndex : 0
    offsetLeft = - (poserId * OFFSET_BASE)
    if (posterContainerRef.current) {
      posterContainerRef.current.style.transform = `translateX(${offsetLeft}vw)`
    }
  }

  useEffect(() => {
    let start: number;
    if (!autoPlay) return;
    // potser switching animation 
    function posterSwitch(timestamp: number) {
      if (start === undefined) {
        start = timestamp;
      }
      let elapsed = timestamp - start;
      if (elapsed > posters.length * time) {
        start = timestamp;
      }
      let posterIndex = Math.floor(elapsed / time) % (posters.length);
      setPosterIndex(posterIndex);
      setPosterTranslate(posterIndex);
      window.requestAnimationFrame(posterSwitch);
    }
    window.requestAnimationFrame(posterSwitch);
  }, [autoPlay, posters.length, time])

  return (
    <div className="Carousel">
      <div className="carousel-wrap">
        <div className="carousel-container" ref={posterContainerRef}>
          {
            posters.map(({ posterId }) => {
              return (
                <Poster key={posterId} posterId={posterId} />
              )
            })
          }
        </div>
      </div>
      <div className="progress-container">
        {
          posters.map(({ posterId }) => {
            return (
              <Progress
                key={`progress${posterId}`}
                pid={posterId}
                pWidth={50}
                pHeight={3}
                autoPlay={autoPlay}
                posterIndex={posterIndex} />
            )
          })
        }
      </div>
    </div>
  )
}