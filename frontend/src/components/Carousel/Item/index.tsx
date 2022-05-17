import React from 'react'

import './index.css'

export type CarouselItemRef = {
  setOffset: React.Dispatch<React.SetStateAction<number>>
}

export type CarouselItemProps = {
  style?: React.CSSProperties
  children: React.ReactNode
}

const CarouselItem = ((props: CarouselItemProps) => {
  const { children, style } = props

  return (<div className="carousel__item" style={style}>
    {children}
  </div>)
})

export default CarouselItem