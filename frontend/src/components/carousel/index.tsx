import React, { ReactElement, useEffect, useState } from "react";
import style from './index.module.scss'
import { IData } from "../../interface";

interface CarouselProps {
  duration?: number // ms
  data: IData[]
}

function Carousel(props: CarouselProps): ReactElement {
  let { data, duration = 3000 } = props;
  let [activeIndex, setActiveIndex] = useState(0);
  let carouselLength = data.length

  useEffect(() => {
    let timer = setTimeout(() => {
      setActiveIndex((activeIndex + 1) % carouselLength)
    }, duration)

    return () => {
      clearTimeout(timer)
    }
  })

  return (
    <div id={style.carousel}>
      {/* 轮播图 */}
      <div className={style.carouselContainer} style={{
        transform: `translateX(-${100 * activeIndex}vw)`
      }}>
        {
          carouselLength ?
            data.map((item, index) => (
              <div className={style.carouselItem} key={index} style={{
                color: item.color,
                backgroundColor: item.bgColor,
                backgroundImage: `url(${item.imgUrl})`
              }}>
                <p className={style.title} dangerouslySetInnerHTML={{ __html: item.title }}></p>
                <p className={style.description}>{item.description}</p>
                <p className={style.price}>{item.price}</p>
              </div>
            ))
            :
            <div id={style.nodata}>nodata</div>
        }
      </div >
      {/* 进度条 */}
      <div className={style.dotWrapper}>
        {
          carouselLength ? data.map((item, index) => (
            <div className={style.dotItem} key={index} >
              <div
                className={[style.dotProgress, activeIndex === index ? style.dotActive : ''].join(' ')}
                style={{ animationDuration: `${duration}ms` }}>
              </div>
            </div>
          )) : null
        }
      </div>
    </div >
  )
}

export default Carousel;