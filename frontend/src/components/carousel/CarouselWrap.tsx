import './CarouselWrap.css'
import React, { ReactComponentElement, ReactElement, useMemo, useState } from 'react'
import CarouselTrack from './CarouselTrack'
import { useMemoizedFn } from '../../utils/hooks'
import CarouselDots from './CarouselDots'
import { useAtuoPlay } from './hooks'
import { CarouselConfigs } from './types'

interface CarouselWrapProps extends CarouselConfigs {
  children: ValidReactChild[]
}

const DefaultConfigs: Required<CarouselConfigs> = {
  carouselStep: 1,
  autoplaySpeed: 3000,
  transitionDuration: 800,
  autoplay: true,
  width: '100%',
  onClickDot: () => {},
}

const getSlideWidth = (props: CarouselConfigs) => {
  const { width } = props
  let fixedWidth = width
  if (width === undefined) {
    fixedWidth = '100%'
  } else if (typeof width === 'number') {
    fixedWidth = width + 'px'
  } else {
    fixedWidth = String(width)
  }
  return fixedWidth
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
    width: getSlideWidth(configs),
    activeIndex: activeIndex,
    slides,
  }

  return (
    <div className="carousel-wrap" style={{ width: mergedConfigs.width }}>
      <CarouselTrack {...mergedConfigs}>{slides}</CarouselTrack>
      <CarouselDots
        {...mergedConfigs}
        onClickDot={handleClickDot}
      ></CarouselDots>
    </div>
  )
}

export default CarouselWrap
