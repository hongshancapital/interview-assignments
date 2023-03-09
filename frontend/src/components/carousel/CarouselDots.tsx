import './CarouselDots.css'
import React, { ReactElement, useCallback, useEffect, useState } from 'react'
import { getKey } from '../../utils'

interface CarouselDotProps {
  children?: ReactElement
  autoplaySpeed: number
  transitionDuration: number
  activeIndex: number
  dataIndex: number
  mounted: boolean
  onClickDot?: (index: number) => void
}

const getCarouselDotStyle = (props: CarouselDotProps) => {
  const { autoplaySpeed, activeIndex, mounted } = props
  const dotIndex = props.dataIndex
  const dotActive = activeIndex === dotIndex
  if (mounted && dotActive) {
    return {
      transform: `translate3d(${
        activeIndex === dotIndex ? '0' : '-100%'
      }, 0, 0)`,
      transitionDuration: autoplaySpeed + 'ms',
      transitionTimingFunction: 'linear',
      boxShadow: '1px 0px 1px #888888',
    }
  }
  return {}
}
const CarouselDot: React.FC<CarouselDotProps> = (props) => {
  const carouselDotStyle = getCarouselDotStyle(props)
  const { dataIndex, onClickDot } = props
  const handleClick = useCallback(() => {
    onClickDot && onClickDot(dataIndex)
  }, [dataIndex, onClickDot])
  return (
    <div className="carousel-dot" onClick={handleClick}>
      <div className="carousel-dot-inner">
        <div className="carousel-dot__process" style={carouselDotStyle}></div>
      </div>
    </div>
  )
}

interface CarouselDotsProps {
  slides: ValidReactChild[]
  autoplaySpeed: number
  transitionDuration: number
  activeIndex: number
  onClickDot?: (index: number) => void
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
          key: 'dots-item-' + getKey(slide, String(index)),
          dataIndex: index,
          tabIndex: '-1',
          style: {},
        }
        return <CarouselDot {...carouselDotProps}></CarouselDot>
      })}
    </div>
  )
}

export default CarouselDots
