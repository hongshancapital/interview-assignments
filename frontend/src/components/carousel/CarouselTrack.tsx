import "./CarouselTrack.css"
import React from "react"
import { ReactElement } from "react"
import { getKey } from "../../utils"

interface CarouselTrackProps {
  children: ReactElement[]
  activeIndex: number
}

const getSlideStyle = (props: CarouselTrackProps, index: number) => {
  let style = {}
  return style
}
const getTrackStyle = (props: CarouselTrackProps) => {
  const { activeIndex } = props
  let style = {
    transform: `translate3d(-${String(activeIndex)}00%, 0, 0)`,
  }
  return style
}
const CarouselTrack: React.FC<CarouselTrackProps> = (props) => {
  const { children } = props
  let slides: ReactElement[] = []
  React.Children.forEach(children, (child, index) => {
    let childStyle = getSlideStyle(props, index)
    slides.push(
      React.cloneElement(<div>{child}</div>, {
        key: "track-item-" + getKey(child, String(index)),
        "data-index": index,
        className: "carousel-slide",
        tabIndex: "-1",
        style: {
          ...childStyle,
        },
      })
    )
  })
  const trackStyle = getTrackStyle(props)
  return (
    <div className="carousel-track" style={trackStyle}>
      {slides}
    </div>
  )
}

export default CarouselTrack
