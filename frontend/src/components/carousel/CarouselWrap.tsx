import './CarouselWrap.css'
import React, { useMemo, useState } from 'react'
import CarouselTrack from './CarouselTrack'
import { useMemoizedFn } from '../../utils/hooks'
import CarouselDots from './CarouselDots'
import { useAtuoPlay } from './hooks'

interface CarouselWrapProps {
  children: ValidReactChild[]
  autoplaySpeed?: number
  transitionDuration?: number
  carouselStep?: number
  onClickDot?: (index: number) => void
  autoplay?: boolean
}

const DefaultConfigs = {
  carouselStep: 1,
  autoplaySpeed: 3000,
  transitionDuration: 800,
  autoplay: true,
}

const CarouselWrap: React.FC<CarouselWrapProps> = (props) => {
  const configs = { ...DefaultConfigs, ...props }
  const {
    children,
    carouselStep,
    autoplaySpeed,
    autoplay,
    onClickDot,
  } = configs

  const [activeIndex, setActiveIndex] = useState(0)

  const slides = useMemo(() => {
    let slides: ValidReactChild[] = []
    React.Children.forEach(children, (child, index) => {
      slides.push(child)
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

  const { autoplayFn, stopplayFn } = useAtuoPlay({
    updateActiveIndex,
    autoplaySpeed,
    autoplay,
  })

  const handleClickDot = useMemoizedFn((newIndex) => {
    stopplayFn && stopplayFn()
    setActiveIndex(newIndex)
    onClickDot && onClickDot(newIndex)
    if (autoplay) {
      autoplayFn()
    }
  })
  const mergedConfigs = {
    ...configs,
    activeIndex: activeIndex,
    slides,
  }
  return (
    <div className="carousel-wrap">
      <CarouselTrack {...mergedConfigs}>{slides}</CarouselTrack>
      <CarouselDots
        {...mergedConfigs}
        onClickDot={handleClickDot}
      ></CarouselDots>
    </div>
  )
}

export default CarouselWrap
