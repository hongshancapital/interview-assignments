import React, { FC } from "react"
import classNames from "classnames"

import "./index.scss"

export interface Props {
  length: number
  currentIndex: number
  duration?: number
}

const CarouselDots: FC<Props> = (props) => {
  const { length, currentIndex, duration = 3 } = props
  const style = {
    "--duration": `${duration}s`,
  } as React.CSSProperties

  return (
    <div className="carousel-dots" style={style}>
      {Array(length)
        .fill("")
        .map((_, index) => {
          const LineClassName = classNames("carousel-dots__item__line", {
            "carousel-dots__item__line--active": currentIndex === index,
          })

          return (
            <div className="carousel-dots__item" key={index}>
              <div className={LineClassName} />
            </div>
          )
        })}
    </div>
  )
}

export default CarouselDots
