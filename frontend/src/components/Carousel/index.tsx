import React, { useState, useEffect, useCallback }  from "react"
import './index.css'

interface ICarousel {
  children: React.ReactElement[],
  speed?: number, // 轮播速度
}

let timer: ReturnType<typeof setInterval>

export default function Carousel({
  children,
  speed = 3000,
}: ICarousel) {
  speed = speed <= 500 ? 500 : speed
  const childrenLen = children && children.length
  const [curIndex, setCurIndex] = useState(0)
  const clear = useCallback(() => timer && clearInterval(timer), [])
  const play = useCallback(() => {
    if (childrenLen) {
      timer = setInterval(() => setCurIndex((index) => (index + 1) % childrenLen), speed);
    }
  }, [childrenLen, speed])

  useEffect(() => {
    play()
    return clear
  }, [play, clear])

  if (!childrenLen) return <div className="carousel"></div>

  return <div className="carousel">
    <div className="carousel-scroll" style={{transform: `translateX(${-curIndex*100}%)`}}>
      { children.map((item, idx) => <div className="carousel-item" key={idx}>{item}</div>) }
    </div>

    <div className="carousel-indicator">
      {children.map((i, index) => {
          const cls = `carousel-indicator-item ${index === curIndex ? 'move' : ''}`
          return <div className={cls} 
            style={{animationDuration: `${speed}ms`}} 
            key={index}></div>
        })
      }
    </div>
  </div>;
}
