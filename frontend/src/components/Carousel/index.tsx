import React, { useEffect, useState } from 'react'
import Style from './index.module.scss'
import { ICarouselProps } from './interface'


export function Carousel ({ duration = 3000, children = [] }: ICarouselProps) {
  // 子节点, 如果不是数组, 转换为数组
  const carouselItemList = children instanceof Array ? children : [children]
  // 数组长度
  const carouselItemLength = carouselItemList.length

  let [activeIndex, setActiveIndex] = useState(-1)

  useEffect(() => {
    // 初始状态为-1
    if (activeIndex < 0) {
      setActiveIndex(0)
    } else {
      const timer = window.setTimeout(() => {
        setActiveIndex((activeIndex + 1) % carouselItemLength)
      }, duration)
      return function clearTimer () {
        window.clearTimeout(timer)
      }
    }
  }, [activeIndex, duration, carouselItemLength])

  return (
    <div className={Style['carousel-panel']}>
      <div className={Style['carousel-content']} style={{transform: `translateX(-${activeIndex * 100}%)`}}>
        { carouselItemList }
      </div>
      {/* 指示器 */}
      <div className={Style['carousel-dot-content']}>
        {
          carouselItemList.map((_, index) => (
            <div key={index} className={Style['carousel-dot-bg']}>
              <div className={`${Style['carousel-dot']} ${index === activeIndex && Style['carousel-dot--active']}`} style={{transitionDuration: `${duration}ms`}}></div>
            </div>
          ))
        }
      </div>
    </div>
  )
}