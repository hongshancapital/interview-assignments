import React, {useMemo, FC} from 'react'
import './index.scss'
import {CarouselProps} from "./type";
import {useCarouselLogic} from "./use-carousel-logic";
import {useIndicatorLogic} from "./use-indicator-logic";

const Prefix = 'carousel'

export const Carousel: FC<CarouselProps> = (props: CarouselProps) => {
  const { className, style, children, contentRef } = useCarouselLogic(props)
  const { } = useIndicatorLogic(props.duration)

  const items = useMemo(() => {
    return children.map((child, index) => (
      <div key={index} className={`${Prefix}__item`}>{child}</div>
    ))
  }, [children])

  return (
    <div className={`${Prefix} ${className || ''}`} style={style}>
      <div className={`${Prefix}__wrapper`}>
        <div className={`${Prefix}__content`} ref={contentRef}>
          {items}
        </div>
      </div>
    </div>
  )
}