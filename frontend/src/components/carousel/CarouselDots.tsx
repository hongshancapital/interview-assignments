import "./CarouselDots.css"
import React, { ReactElement, useEffect, useState } from "react"
import { getKey } from "../../utils"

interface CarouselDotProps {
  children?: ReactElement
  autoplaySpeed: number
  transitionDuration: number
  activeIndex: number
  "data-index": number
  mounted: boolean
}

const getCarouselDotStyle = (props: CarouselDotProps) => {
  const { autoplaySpeed, activeIndex, mounted } = props
  const dotIndex = props["data-index"]
  const dotActive = activeIndex === dotIndex
  if (mounted && dotActive) {
    return {
      transform: `translate3d(${
        activeIndex === dotIndex ? "0" : "-100%"
      }, 0, 0)`,
      transitionDuration: autoplaySpeed + "ms",
      transitionTimingFunction: "linear",
      boxShadow: "1px 0px 1px #888888",
    }
  }
  return {}
}
const CarouselDot: React.FC<CarouselDotProps> = (props) => {
  const carouselDotStyle = getCarouselDotStyle(props)
  return (
    <div className="carousel-dot">
      <div className="carousel-dot__process" style={carouselDotStyle}></div>
    </div>
  )
}

interface CarouselDotsProps {
  slides: React.ReactElement[]
  autoplaySpeed: number
  transitionDuration: number
  activeIndex: number
}

const CarouselDots: React.FC<CarouselDotsProps> = (props) => {
  const { slides } = props
  const [mounted, setMounted] = useState(false)
  useEffect(() => {
    setMounted(true)
  }, [])
  return (
    <div className="carousel-dots">
      {slides.map((slide, index) => {
        const carouselDotProps = {
          ...props,
          mounted,
          key: "dots-item-" + getKey(slide, String(index)),
          "data-index": index,
          tabIndex: "-1",
          style: {},
        }
        return <CarouselDot {...carouselDotProps}></CarouselDot>
      })}
    </div>
  )
}

export default CarouselDots
