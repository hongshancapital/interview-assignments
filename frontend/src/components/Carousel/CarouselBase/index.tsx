import React, { FC, useEffect, useState } from "react"
import classNames from "classnames"

import CarouselDots from "../CarouselDots"
import "./index.scss"

export interface Props {
  children?: React.ReactNode
  duration?: number
}

const CarouselBase: FC<Props> = (props) => {
  const { children, duration = 3 } = props
  const itemsLength = React.Children.count(children)

  const [currentIndex, setCurrentIndex] = useState(0)

  useEffect(() => {
    const timer = setInterval(() => {
      const nextIndex = (currentIndex + 1) % itemsLength

      setCurrentIndex(nextIndex)
    }, duration * 1000)

    return () => {
      clearInterval(timer)
    }
  }, [currentIndex])

  const DotClassName = classNames("carousel-base__dots", {
    "carousel__dots--hidden": !itemsLength,
  })

  return (
    <div className="carousel-base">
      <div
        className="carousel-base__content"
        style={{
          transform: `translateX(-${currentIndex * 100}%)`,
        }}
      >
        {!!itemsLength &&
          React.Children.map(children, (item, index) => {
            return (
              <div key={index} className="carousel-base__content__item">
                {item}
              </div>
            )
          })}
        {!itemsLength && <div className="carousel-base__empty">Empty!</div>}
      </div>

      <div className={DotClassName}>
        <CarouselDots
          duration={duration}
          length={itemsLength}
          currentIndex={currentIndex}
        />
      </div>
    </div>
  )
}

export default CarouselBase
