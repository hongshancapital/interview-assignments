import React from 'react'
import Style from './index.module.scss'
import { ICarouselItemProps } from "./interface"

export function CarouselItem ({ title = "", description = "", image, background, color }: ICarouselItemProps) {
  return (
    <div className={Style['carousel-item']} style={{background, color}}>
      <div className={Style['item-title']} style={{whiteSpace: 'pre-wrap'}}>
        {
          title.split('\\n').map((item, index) => (<span key={index}>{item}<br/></span>))
        }
      </div>
      <div className={Style['item-description']}>
        {
          description.split('\\n').map((item, index) => (<span key={index}>{item}<br/></span>))
        }
      </div>
      <div className={Style['item-image']}>
        <img src={image} alt={description} />
      </div>
    </div>
  )
}