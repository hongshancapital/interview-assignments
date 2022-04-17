import React, {useEffect, useLayoutEffect, useRef, useState} from "react";
import "./index.scss";
import {CarouselProps, ICarouselItem, IIndicatorItemProps} from "./data";

/**
 * 自适应获取屏幕宽高
 */
function useWindowSize() {
  const [size, setSize] = useState([0, 0])

  const updateSize = () => {
    setSize([window.innerWidth, window.innerHeight])
  }

  useLayoutEffect(() => {
    window.addEventListener('resize', updateSize)
    updateSize()
    return () => window.removeEventListener('resize', updateSize)
  }, [])

  return size
}

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
const CarouselItem:React.FC<ICarouselItem> = ({width, title, subTitle, pic, className}) => {
  return (
      <div className="carousel-item" style={{width}}>
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

const Carousel:React.FC<CarouselProps> = ({items:carouselItems, duration = 3000, speed = 400}) => {
  const [winWidth, winHeight] = useWindowSize()
  const [currentIndex, setCurrentIndex] = useState(0)
  const itemCount = carouselItems.length

  useInterval(() => {
    setCurrentIndex(oldIndex => (oldIndex + 1) % itemCount)
  }, duration)

  return <div className="carousel-wrap">
    <div className="carousel-list" style={{
      width: winWidth * 3,
      height: winHeight,
      transform: `translateX(-${winWidth * currentIndex}px)`,
      transition: `transform ${speed}ms ease`
    }}>
      {
        carouselItems.map((item, index) => <CarouselItem key={item.title + index} {...item} width={winWidth}/>)
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
