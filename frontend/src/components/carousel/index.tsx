import React, {useMemo, FC} from 'react'
import './index.scss'
import {CarouselProps} from "./type";
import {useCarouselLogic} from "./use-carousel-logic";
import {useIndicatorLogic} from "./use-indicator-logic";

const Prefix = 'carousel'

export const Carousel: FC<CarouselProps> = (props: CarouselProps) => {
  const { className, style } = props
  const { children, contentStyle,onContentTransitionEnd, activeIndex, showIndicator } = useCarouselLogic(props)
  const { progressStyle, childCount } = useIndicatorLogic(props)

  const items = useMemo(() => {
    return children.map((child, index) => (
      <div key={index} className={`${Prefix}__item`}>{child}</div>
    ))
  }, [children])

  const indicators = useMemo(() => {
    if(!showIndicator){
      return undefined
    }

    const list: React.ReactNode[] = []
    for (let index = 0; index < childCount; index++) {
      list.push(
        <div
          key={index}
          className={`${Prefix}__indicator`}
        >
          <div
            className={`${Prefix}__indicator-progress`}
            style={activeIndex === index ? progressStyle : undefined}
          />
        </div>
      )
    }
    return (
      <div className={`${Prefix}__indicator-wrapper`}>
        {list}
      </div>
    )
  },[activeIndex, progressStyle, childCount, showIndicator])

  return (
    <div className={`${Prefix} ${className || ''}`} style={style}>
      <div className={`${Prefix}__wrapper`}>
        <div className={`${Prefix}__content`} style={contentStyle} onTransitionEnd={onContentTransitionEnd}>
          {items}
        </div>
        {indicators}
      </div>
    </div>
  )
}