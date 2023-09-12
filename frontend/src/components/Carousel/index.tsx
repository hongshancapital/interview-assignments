import React, { useState, useEffect, useRef, useMemo } from "react"

import "./index.scss"

interface CarouselProps {
  children: React.ReactNode
  autoPlay?: boolean
}
const Carousel: React.FC<CarouselProps> = (props) => {
  // 当前正在显示item的索引
  const [index, setIndex] = useState(0)
  const timerRef = useRef(0)
  const counts = useMemo(() => {
    let count = 0
    React.Children.forEach(props.children, () => {
      count++
    })
    return count
  }, [props.children])

  const handleDotClick = (i: number) => {
    if (index === i) return
    if (props.autoPlay) startTimer()
    setIndex(i)
  }

  // 开启定时器
  const startTimer = () => {
    if (timerRef.current) stopTimer()
    timerRef.current = window.setInterval(() => {
      setIndex((index) => {
        if (index >= 2) {
          return 0
        }
        return index + 1
      })
    }, 3000)
  }

  // 停止定时器
  const stopTimer = () => {
    window.clearInterval(timerRef.current)
  }

  useEffect(() => {
    if (props.autoPlay) {
      startTimer()
    }
    return () => {
      if (props.autoPlay) {
        stopTimer()
      }
    }
  }, [])

  return (
    <div className="carousel">
      <div
        className="carousel__inner"
        style={{
          width: `${counts * 100}vw`,
          transform: `translateX(-${index * 100}vw)`,
        }}
      >
        {props.children}
      </div>
      <div className="carousel__dot">
        {React.Children.map(props.children, (_, i) => {
          return (
            <span
              key={i}
              // className={`${styled.item} ${i === index ? styled.active : ""}`}
              className={`carousel__item ${
                i === index ? "carousel__item--active" : ""
              }`}
              onClick={() => handleDotClick(i)}
            ></span>
          )
        })}
      </div>
    </div>
  )
}

Carousel.defaultProps = {
  autoPlay: false,
}

export default Carousel
