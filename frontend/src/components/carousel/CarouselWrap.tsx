import "./CarouselWrap.css"
import React, { useEffect, useMemo, useRef, useState } from "react"
import { ReactElement } from "react"
import CarouselTrack from "./CarouselTrack"
import { useMemoizedFn } from "../../utils/hooks"
import CarouselDots from "./CarouselDots"

interface CarouselWrapProps {
  children: ReactElement | ReactElement[]
  autoplaySpeed?: number
  transitionDuration?: number
  carouselStep?: number
}

const DefaultConfigs = {
  carouselStep: 1,
  autoplaySpeed: 3000,
  transitionDuration: 800,
}

const CarouselWrap: React.FC<CarouselWrapProps> = (props) => {
  const configs = { ...DefaultConfigs, ...props }
  const { children, carouselStep, autoplaySpeed } = configs

  const [activeIndex, setActiveIndex] = useState(0)
  const carouselTimerRef = useRef<null | NodeJS.Timer>(null)

  const slides = useMemo(() => {
    let slides: ReactElement[] = []
    React.Children.forEach(children, (reactElement, index) => {
      slides.push(reactElement)
    })
    return slides
  }, [children])

  const updateActiveIndex = useMemoizedFn(() => {
    let nextActiveIndex = activeIndex + carouselStep
    const childrenCount = React.Children.count(children)
    if (nextActiveIndex > childrenCount - 1) {
      nextActiveIndex = nextActiveIndex - childrenCount
    }
    setActiveIndex(nextActiveIndex)
  })
  useEffect(() => {
    carouselTimerRef.current = setInterval(updateActiveIndex, autoplaySpeed)
    return () => {
      if (carouselTimerRef.current !== null) {
        clearInterval(carouselTimerRef.current)
        carouselTimerRef.current = null
      }
    }
  }, [autoplaySpeed, updateActiveIndex])
  const mergedConfigs = {
    ...configs,
    activeIndex: activeIndex,
    slides,
  }
  return (
    <div className="carousel-wrap">
      <CarouselTrack {...mergedConfigs}>{slides}</CarouselTrack>
      <CarouselDots {...mergedConfigs}></CarouselDots>
    </div>
  )
}

export default CarouselWrap
