import React, {useEffect, useRef, useState} from "react";
import "./index.scss";
import {CarouselProps, ICarouselItem, IIndicatorItemProps} from "./data";

function useInterval(callback: () => void, delay: number) {
  const intervalRef = useRef(-1)
  const callbackRef = useRef(callback)

  useEffect(() => {
    callbackRef.current = callback
  }, [callback])

  useEffect(() => {
    intervalRef.current = window.setInterval(() => callbackRef.current(), delay)
    return () => window.clearInterval(intervalRef.current)
  }, [delay]);
}

// 轮播图的每一项
const CarouselItem:React.FC<ICarouselItem> = ({title, subTitle, pic, className}) => {
  return (
      <div className="carousel-item">
        <div className={`ci-title-wrap ${className}`}>
          <p className="ci-title">{ title }</p>
          <p className="ci-sub-title">{ subTitle }</p>
        </div>
        <img src={pic} alt="轮播图"/>
      </div>
  )
}

// 指示器的每一项
const IndicatorItem:React.FC<IIndicatorItemProps> = ({active, duration}) => {
  return (
      <div className="indicator-item">
          <span className={active ? 'active' : ''} style={{
            animationDuration: `${duration}ms`
          }}/>
      </div>
  )
}

const Carousel:React.FC<CarouselProps> = ({items:carouselItems, duration = 3000}) => {
  const [currentIndex, setCurrentIndex] = useState(0)
  const itemCount = carouselItems.length

  useInterval(() => {
    setCurrentIndex(oldIndex => (oldIndex + 1) % itemCount)
  }, duration)

  return <div className="carousel-wrap">
    <div className={`carousel-list active-index-${currentIndex}`}>
      {
        carouselItems.map((item, index) => <CarouselItem key={item.title + index} {...item}/>)
      }
    </div>
    <div className="indicator">
      {
        carouselItems.map((item, index) => <IndicatorItem key={item.title + index} active={currentIndex === index} duration={duration}/>)
      }
    </div>
  </div>;
}

export default Carousel;
