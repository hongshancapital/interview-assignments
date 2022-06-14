import React from 'react'
import './styles/CarouselItem.css'

interface IsCarouselItemProps {
  children: JSX.Element
}

export default function CarouselItem(props: IsCarouselItemProps) {
  return <div className='carousel-item'>
    {props.children}
  </div>
}