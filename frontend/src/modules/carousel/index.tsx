import React, { FC, useState } from 'react'
import './index.css'
import { ICarouselItem } from '../../types/carousel'
import Carousel from '../../components/carousel';

/**
 * modules下为业务模块，不涉及特效交互逻辑
 */
const CarouselItem: FC<ICarouselItem> = (item) => {
  return <div
    className="carousel-item"
    data-testid="carousel-item"
    style={{
      color: item.fontColor,
      backgroundColor: item.bgColor
    }}>
    <div className="carousel-item-info">
      <div className="carousel-item-title">
        {item.title}
      </div>
      {
        item.desc && <div className="carousel-item-desc">
          {item.desc}
        </div>
      }
    </div>
    <div
      className="carousel-item-img"
      style={{ backgroundImage: `url(${item.bgImg})` }}
    ></div>
  </div >
}

interface IProps {
  carouselData?: ICarouselItem[]
}

const CarouselModule: FC<IProps> = (props) => {
  const { carouselData = [] } = props

  return (
    <div className="carousel-wrapper" data-testid="carousel-wrapper">
      <Carousel duration={3000}>
        {
          carouselData.map(item => {
            return <CarouselItem key={item.id} {...item}></CarouselItem>
          })
        }
      </Carousel>
    </div >
  )
}

export default CarouselModule
