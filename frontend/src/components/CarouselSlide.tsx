import React, { ClassAttributes, useRef } from 'react'
import { useEffectOnce } from 'react-use'
import { useContainer } from 'unstated-next'
import { filterClassName } from '../utils/filterClassName'
import { CarouselContainer } from './Carousel'
import style from './CarouselSlide.module.css'

export interface ICarouselSlideProps extends ClassAttributes<ICarouselSlideProps> {
  className?: string,
}

export const CarouselSlide: React.FC<ICarouselSlideProps> = (props) => {
  const { slides, slideActions } = useContainer(CarouselContainer)
  const slideRef = useRef<HTMLDivElement>(null)

  useEffectOnce(() => {
    slideActions.push(slideRef)
    return () => slideActions.removeAt(slides.findIndex((ref) => ref === slideRef))
  })

  return (
    <div className={filterClassName(props.className, style.corouselSlide)} ref={slideRef}>
      {props.children}
    </div>
  )
}

export default CarouselSlide