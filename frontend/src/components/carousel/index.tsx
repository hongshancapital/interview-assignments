import React, { ReactElement, useEffect, useState } from "react";
import style from './index.module.scss'
import { IData } from "../../interface";


/**
 * @interface CarouselProps
 * @param duration 轮换间隔，默认3000毫秒
 * @param data 轮播单元的数据列表 IData[]
 */
interface CarouselProps {
  duration?: number
  data: IData[]
}

function Carousel(props: CarouselProps): ReactElement {
  // props
  let { data, duration = 3000 } = props;

  // state
  let [activeIndex, setActiveIndex] = useState(0);

  // custom variables
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
      {/* 轮播，空数据显示nodata占位 */}
      <div className={style.carouselContainer} style={{
        transform: `translateX(-${100 * activeIndex}vw)`
      }}>
        {
          carouselLength ?
            data.map((item, index) => (
              <div className={style.carouselItem} key={index} style={{
                color: item.color,
                backgroundColor: item.bgColor,
              }}>
                <div className={style.textbox}>
                  <p className={style.title}>
                    {
                      item.title.split('\n').map((element, i) => (<span key={i}>{element}<br /></span>))
                    }
                  </p>
                  <p className={style.description}>{item.description}</p>
                  <p className={style.price}>{item.price}</p>
                </div>
                <div className={style.image} style={{ backgroundImage: `url(${item.imgUrl})` }}></div>
              </div>
            ))
            :
            <div id={style.nodata}>nodata</div>
        }
      </div >
      {/* 进度条，空数据情况不展示; 一条数据不展示； */}
      <div className={style.dotWrapper}>
        {
          carouselLength > 1 ? data.map((item, index) => (
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