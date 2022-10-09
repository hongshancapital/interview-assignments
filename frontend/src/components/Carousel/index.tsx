import React, {CSSProperties, FC, useMemo, useState} from 'react'
import {userInterval} from '../../hooks'

import './index.scss'

interface ICarouselItemProps {
  style?: CSSProperties
  index?: number
  title: string
  text?: string
}

export const CarouselItem: FC<ICarouselItemProps> = ({index, style, title, text}) => {
  return (
    <div className={`carousel-item carousel-item-${index}`} style={style}>
      <div className="title">{title}</div>
      <div className="text">{text}</div>
    </div>
  )
}

interface ICarouselProps {
  text?: string
  style?: CSSProperties
  time?: number
}

const Carousel: FC<ICarouselProps> = ({time = 3000, style, children}) => {
  const [currentIndex, setCurrentIndex] = useState(0)
  const length = useMemo(() => React.Children.count(children), [children])

  userInterval(() => {
    setCurrentIndex((currentIndex) => {
      return (currentIndex + 1) % length
    })
  }, time)

  return (
    <div style={style} className="carousel">
      <div className="carousel-wrapper">
        {React.Children.map(children, (child, index) => {
          if (!React.isValidElement(child)) return null
          if (child.type !== CarouselItem) return null

          return <CarouselItem {...child.props} index={index === currentIndex ? 0 : index - currentIndex} />
        })}
      </div>
      <div className="carousel-dots">
        {React.Children.map(children, (child, index) => {
          if (!React.isValidElement(child)) return null
          return (
            <div
              key={index}
              style={{animationDuration: `${time / 1000}s`}}
              onClick={() => setCurrentIndex(index)}
              className={`carousel-dots-item ${index === currentIndex ? 'active-dots' : ''}`}
            ></div>
          )
        })}
      </div>
    </div>
  )
}

export default Carousel
