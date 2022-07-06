import React, { useState, useEffect, useRef, useCallback } from "react";
import "./../css/Carousel.css";
import { carouselProps } from '../type'
import CarouselItem from './CarouselItem';
import IndexDotItem from './IndexDotItem';
function Carousel(props: carouselProps) {
  const [currIndex, setCurrIndex] = useState<number>(0)
  const [countdown, setCountdown] = useState<number>(0)
  const [speed] = useState<number>(props.speed ? props.speed : 60)
  const timeOut = useRef<any>(null);
  const tabChange = (index: number): void => {
    setCurrIndex(index)
    setCountdown(0)
  }
  const stopAnimation = useCallback(() => {
    if (timeOut.current) {
      clearInterval(timeOut.current)
      timeOut.current = null
    }
  }, [])
  const startAnimation = useCallback(() => {
    if (!timeOut.current) {
      timeOut.current = setTimeout(() => {
        if (countdown < 50) {
          setCountdown(countdown + 1)
        } else {
          setCurrIndex(currIndex < 2 ? currIndex + 1 : 0)
          setCountdown(0)
        }
      }, speed)
    }
  }, [currIndex, countdown, speed])

  useEffect(() => {
    startAnimation()
    return () => {
      stopAnimation()
    }
  })
  const items = props.carouselList.map((data) =>
    <CarouselItem key={data.id} {...data} />
  )
  const dotItems = props.carouselList.map((data, index) =>
    <IndexDotItem key={`${data.id}dot`} currIndex={currIndex} countdown={countdown} index={index} tabChange={tabChange} startAnimation={startAnimation} stopAnimation={stopAnimation} />
  )
  return <div className="carousel-wrap">
    <ul className={`carousel-list pos-${currIndex}`} >
      {items}
    </ul>
    <ul className="indexDot">
      {dotItems}
    </ul>
  </div>;
}

export default Carousel;
