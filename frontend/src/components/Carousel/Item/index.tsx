import React from 'react'

import './index.scss'

export type CarouselItemRef = {
  setOffset: React.Dispatch<React.SetStateAction<number>>
}

export type CarouselItemProps = {
  children: React.ReactNode
}

const CarouselItem = ((props: CarouselItemProps) => {
  const { children } = props

  return (<div className="carousel__item">
    {children}
  </div>)
})

export default CarouselItem