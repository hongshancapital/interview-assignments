import React, { FC, ReactNode, useCallback, useMemo, useState } from 'react'
// import classnames from 'classnames'
import './index.css'
import { MockData } from '../../mock/data'
import { ICarouselItem } from '../../types/carousel'
import Carousel from '../../components/carousel';


interface IProps {
  duration?: number
}

const bannerDuration = 300;

/**
 * modules下为业务模块，不涉及前端交互逻辑
 */
const CarouselModule: FC<IProps> = (props) => {
  const [carouselData] = useState<ICarouselItem[]>(MockData)

  return (
    <div className="carousel-wrapper">
      <Carousel duration={3000}>
        {
          carouselData.map(item => (
            <div
              className="carousel-item"
              key={item.id}
              style={{ backgroundColor: item.bgColor, color: item.fontColor }}>
              <div className="carousel-item-info">
                <div className="carousel-item-title">
                  {item.title}
                </div>
                {
                  item.desc && <div className="carousel-item-desc">
                    {item.desc.map(text => <p>${text}</p>)}
                  </div>
                }
              </div>
              <div
                className="carousel-item-img"
                style={{ backgroundImage: `url(${item.bgImg})` }}
              ></div>
            </div>
          ))
        }
      </Carousel>
    </div >
  )
}

export default CarouselModule
