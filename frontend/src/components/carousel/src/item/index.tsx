import { FC, CSSProperties } from 'react'
import classnames from 'classnames'

import { getReverseColor } from '@/utils/index'

import type { CarouselItemProps } from '../typing'

import Img from '@/components/img'

import './index.scss'

const CarouselItem: FC<CarouselItemProps> = ({ title, desc = '', bgImg, bgColor, active = false, idx }) => {
  const SCREEN_WIDTH = window.screen.width

  const containerStyle: CSSProperties = {
    backgroundColor: bgColor,
    width: SCREEN_WIDTH,
    left: (idx || 0) * SCREEN_WIDTH,
  }

  const textStyle: CSSProperties = {
    color: getReverseColor(bgColor),
  }

  return (
    <div className={classnames('carousel-item-container', { active })} style={containerStyle}>
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
