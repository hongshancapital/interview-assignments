import React, { useState, useMemo, useRef, useCallback, useEffect } from 'react'
import { Dots } from './Dots'
import './index.css'

interface CarouselProps {
  duration?: number
  children?: JSX.Element[]
}

const Carousel = (props: CarouselProps) => {
  const { duration, children } = props
  const [activeIndex, setActiveIndex] = useState(0)
  const childCount = useMemo(() => React.Children.count(children), [children])
  const contentRef = useRef<HTMLDivElement | null>(null)
  const isDisabledAutoPlay = useMemo(() => {
    return childCount <= 1
  }, [childCount])

  const handleChild = (
    item:
      | string
      | number
      | React.ReactElement<any, string | React.JSXElementConstructor<any>>
      | React.ReactFragment
      | React.ReactPortal,
    index: number
  ) => {
    return (
      <div key={index} className="carousel__item-wrapper">
        {item}
      </div>
    )
  }

  const handleOriginChildren = useMemo(() => {
    const childArray = React.Children.toArray(children)
    const originGroup = [...childArray].map((item, index) => {
      return handleChild(item, index)
    })
    return [
      ...originGroup,
      [childArray[0]].map((item, index) => handleChild(item, index)),
    ]
  }, [children])

  const movePercent = (targetIndex: number) => {
    return `${-targetIndex * 100}%`
  }

  const toIndex = useCallback(
    (newIndex: number) => {
      if (newIndex === activeIndex) {
        return
      }
      if (contentRef.current) {
        contentRef.current.style.transform = `translateX(${movePercent(
          newIndex
        )})`
        setActiveIndex(newIndex)
      }
    },
    [activeIndex]
  )

  useEffect(() => {
    let timer: NodeJS.Timer | null = null
    if (duration && !isDisabledAutoPlay) {
      timer = setInterval(() => {
        toIndex(activeIndex === childCount - 1 ? 0 : activeIndex + 1)
      }, duration)
      return () => clearInterval(Number(timer))
    }
  }, [activeIndex, childCount, duration, isDisabledAutoPlay, toIndex])

  const dotsOnClick = (target: number) => {
    toIndex(target)
  }

  return (
    <div className="carousel">
      <div className="carousel__wrapper">
        <div className="carousel__content" ref={contentRef}>
          {handleOriginChildren}
        </div>
        <Dots
          activeIndex={activeIndex}
          count={childCount}
          onClick={dotsOnClick}
          duration={duration}
        />
      </div>
    </div>
  )
}

export default Carousel
