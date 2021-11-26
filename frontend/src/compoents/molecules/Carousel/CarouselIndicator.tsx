import React, { FC, useMemo } from 'react';
import css from './Carousel.module.scss'

export interface CarouselIndicatorProps {
  childLength: number
  activeIdx: number
  progress: number
  changeIdx: (idx: number) => void
}


export const CarouselIndicator: FC<CarouselIndicatorProps> = (props) => {
  const { childLength, activeIdx, progress, changeIdx } = props
  const renderIndicator = useMemo(() => {
    const elements: JSX.Element[] = []
    for (let i = 0; i < childLength; i++) {
      elements.push(<div onClick={() => { i !== activeIdx - 1 && changeIdx(i + 1) }} key={i} className={css['indicator-item']}>
        <div className={css['progress-bar']} style={{ width: i === activeIdx - 1 ? progress + '%' : 0 }}/>
      </div>)
    }
    return elements
  }, [activeIdx, changeIdx, childLength, progress])
  return <div className={css['carousel-indicator-container']}>
    {renderIndicator}
  </div>
}