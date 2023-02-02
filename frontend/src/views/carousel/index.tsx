import React, { FC } from 'react'
import './index.css'
import { ICarouselItem } from '../../types/carousel.d'
import Carousel from '../../components/carousel'

/**
 * 每页显示的模块
 * @param item 每个模块的数据
 */
const CarouselItem: FC<ICarouselItem> = (item) => {
  const {
    title,
    desc,
    fontColor = '#fff',
    bgColor = '#000',
    bgImg,
  } = item;

  return <div
    className="carousel-item"
    data-testid="carousel-item"
    style={{
      color: fontColor,
      backgroundColor: bgColor
    }}>
    <div className="carousel-item-info">
      <div className="carousel-item-title">
        {title}
      </div>
      {
        desc && <div className="carousel-item-desc">
          {desc}
        </div>
      }
    </div>
    <div
      className="carousel-item-img"
      style={{ backgroundImage: `url(${bgImg})` }}
    ></div>
  </div >
}

interface IProps {
  carouselData?: ICarouselItem[]
}

const DURATION = 3000;

/**
 * 轮播图业务模块
 * @param carouselData 轮播图的列表数据
 */
const CarouselModule: FC<IProps> = ({ carouselData = [] }) => {
  return (
    <Carousel
      duration={DURATION}
      carouselIds={carouselData.map(item => item.id)}>
      {
        carouselData.map(item => {
          return <CarouselItem key={item.id} {...item}></CarouselItem>
        })
      }
    </Carousel>
  )
}

export default CarouselModule
