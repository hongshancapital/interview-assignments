import React, { FC, useCallback, useEffect, useState } from 'react'
import './index.css'

interface CarouselProps {
  duration?: number
  flashNext?: number
  children?: React.ReactNode
}

export const Carousel: FC<CarouselProps> = (props: CarouselProps) => {
  const { duration = 3000, flashNext = 500, children } = props

  const slidesLength = React.Children.count(children)

  const [currentSlide, setCurrentSlide] = useState<number>(0)

  const scrollSlides = useCallback(() => {
    setCurrentSlide((currentSlide + 1) % slidesLength)
  }, [currentSlide, slidesLength])

  useEffect(() => {
    let carouselTimer: number = window.setInterval(scrollSlides, duration)
    return () => clearInterval(carouselTimer)
  }, [currentSlide, duration, scrollSlides])

  return (
    <div className="carousel-container">
      <div
        className="carousel"
        style={{
          width: `${slidesLength * 100}%`,
          transitionDuration: `${flashNext}ms`,
          transform: `translateX(-${(currentSlide / slidesLength) * 100}%)`,
        }}
      >
        {children}
      </div>

      <div className="carousel-indicator">
        {React.Children.map(children, (_, index: number) => {
          const isActive = currentSlide === index ? ' active' : ''
          return (
            <div
              key={index}
              className={`carousel-indicator__item${isActive}`}
              style={{ animationDuration: `${duration}ms` }}
            ></div>
          )
        })}
      </div>
    </div>
  )
}

export default Carousel
