import React, { FC } from "react"
import classNames from "classnames"

import { createPropsGetter } from "../../../utils/createPropsGetter"
import "./index.scss"

interface IProps {
  length: number
  currentIndex: number
}

const defaultProps = {
  duration: 5,
}

type DefaultProps = Readonly<typeof defaultProps>

export type Props = IProps & Partial<DefaultProps>

const getProps = createPropsGetter<DefaultProps>()

const CarouselDots: FC<Props> = (props) => {
  const { length, currentIndex, duration } = getProps(props)
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

CarouselDots.defaultProps = defaultProps

export default CarouselDots
