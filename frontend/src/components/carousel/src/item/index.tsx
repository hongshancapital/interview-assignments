import { FC, CSSProperties } from 'react'

import { getReverseColor } from '@/utils/index'
import { useResizeWindow } from '@/hooks/useResizeWindow'

import type { CarouselItemProps } from '../typing'

import Img from '@/components/img'

import './index.scss'

const CarouselItem: FC<CarouselItemProps> = ({ title, desc = '', bgImg, bgColor, active = false, idx }) => {
  const { screenWidth } = useResizeWindow()

  const containerStyle: CSSProperties = {
    backgroundColor: bgColor,
    width: screenWidth,
    left: (idx || 0) * screenWidth,
  }

  const textStyle: CSSProperties = {
    color: getReverseColor(bgColor),
  }

  return (
    <div className="carousel-item-container" style={containerStyle}>
      <div>
        <div className="title" style={textStyle}>
          {title}
        </div>
        <div className="text" style={textStyle}>
          {desc}
        </div>
      </div>

      {bgImg && <Img className="img" src={bgImg} />}
    </div>
  )
}

export default CarouselItem
