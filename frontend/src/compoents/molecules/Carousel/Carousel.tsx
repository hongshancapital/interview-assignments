import React, {FC, useMemo, useRef} from 'react'
import {useCarouselController, useCarouselTransition, useIndicators} from './useCarousel'
import {CarouselIndicator} from './CarouselIndicator'
import css from './Carousel.module.scss'

type Direction = 'ltr' | 'rtl'

export interface CarouselConfig {
  autoPlay?: boolean
  dir?: Direction
  duration?: number
  hoverPause?: boolean
  infinite?: boolean
  showIndicators?: boolean
}

export interface CarouselProps extends CarouselConfig {
  // There may be a pass value other than other configurations
}

export const Carousel: FC<CarouselProps> = (props) => {
  const { children, duration = 3000, showIndicators = true } = props

  const childLength = useMemo(() => React.Children.count(children), [children])

  const renderCarouselChild = useMemo<JSX.Element[]>(() => {
    if (childLength === 0) {
      return []
    }
    const elements: JSX.Element[] = []
    React.Children.forEach(children, (child, idx) => {
      elements.push(<div key={`slide-item-${idx}`} className={css['slide-item']}>
        { child }
      </div>)
      if (idx === childLength - 1) {
        elements.unshift(
          <div key={`cloned-slide-item-${idx}`} className={css['slide-item']}>
            { child }
          </div>
        )
      }
    })
    return elements
  }, [childLength, children])

  const carouselElRef = useRef<HTMLDivElement>(null)
  const { idxInfo, changeCarouselIdx } = useCarouselController(props, childLength, carouselElRef)
  const { curProgress } = useIndicators(idxInfo, duration, showIndicators)
  const { animationStyle } = useCarouselTransition(idxInfo, childLength)

  return (
    <div ref={carouselElRef} className={css['carousel-container']}>
      <div className={css['slide-container']} style={animationStyle}>
        {renderCarouselChild}
      </div>
      {
        showIndicators && <CarouselIndicator changeIdx={changeCarouselIdx} activeIdx={idxInfo.nextIdx} childLength={childLength} progress={curProgress}/>
      }
    </div>
  )
}