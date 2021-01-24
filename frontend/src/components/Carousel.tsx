import pick from 'object.pick'
import React, { ClassAttributes, RefObject, useCallback, useEffect, useState } from 'react'
import { useCounter, useList } from 'react-use'
import { createContainer, useContainer } from 'unstated-next'
import { filterClassName } from '../utils/filterClassName'
import style from './Carousel.module.css'

function useCarouselStates() {
  const [slides, slideActions] = useList<RefObject<HTMLDivElement>>([])
  const [activeIndex, setActiveIndex] = useState(0)
  const [slideInterval, setSlideInterval] = useState(3000)
  const [refreshTimer, { inc: triggerRefreshTimer }] = useCounter();

  useEffect(() => {
    if (slideInterval <= 0) return

    const handler = setInterval(() => setActiveIndex((i) => ++i % slides.length), slideInterval)
    return () => clearInterval(handler)
  }, [slides, slideInterval, refreshTimer])

  const updateActiveIndex = useCallback((index: number) => {
    setActiveIndex(index)
    triggerRefreshTimer()
  }, [triggerRefreshTimer])

  return {
    activeIndex, updateActiveIndex,
    slides, slideActions,
    slideInterval, setSlideInterval,
  }
}

export const CarouselContainer = createContainer(useCarouselStates)

export interface CarouselWrapper extends ClassAttributes<CarouselWrapper> {
  interval?: number,
  activeIndex?: number,
  onActiveIndexChange?: (activeIndex: number) => void,
}

const CarouselWrapper: React.FC<CarouselWrapper> = ({ children, interval, activeIndex: propActiveIndex, onActiveIndexChange }) => {
  const { slides, activeIndex, updateActiveIndex, slideInterval, setSlideInterval } = useContainer(CarouselContainer)

  useEffect(() => {
    if (typeof interval === 'number') setSlideInterval(interval)
  }, [interval, setSlideInterval])

  useEffect(() => {
    if (propActiveIndex) updateActiveIndex(propActiveIndex)
  }, [propActiveIndex, updateActiveIndex])

  useEffect(() => {
    onActiveIndexChange && onActiveIndexChange(activeIndex)
  }, [activeIndex, onActiveIndexChange])

  return (
    <>
      <div className={style.carouselSlideWrapper} style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
        { children }
      </div>
      <div className={style.carouselIndicators}>
        { slides.map((_, i) => {
          return (
            <div key={i} className={style.carouselIndicatorItem} onClick={() => updateActiveIndex(i)}>
              <div className={style.carouselIndicatorItemBackground}>
                <div className={style.carouselIndicatorItemFill} style={{
                  ...(activeIndex === i ? { animationDuration: `${slideInterval}ms`, animationName: style.progress } : {}),
                }}></div>
              </div>
            </div>
          )
        }) }
      </div>
    </>
  )
}


export interface ICarouselProps extends CarouselWrapper {
  className?: string,
}

export const Carousel: React.FC<ICarouselProps> = (props) => {
  return (
    <CarouselContainer.Provider>
      <div className={filterClassName(props.className, style.carouselContainer)}>
        <CarouselWrapper {...pick(props, ['interval', 'activeIndex', 'onActiveIndexChange'])}>{ props.children }</CarouselWrapper>
      </div>
    </CarouselContainer.Provider>
  )
}

export default Carousel