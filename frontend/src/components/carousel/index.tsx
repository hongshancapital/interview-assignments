import React, { useState, useEffect } from 'react'
import { CarouselItem } from '../../types/carousel'
import './style.css'
interface Props {
  speed?: number // 滚动间隔
  carouselList: CarouselItem[] // 数据源
  direction?: 'horizontal' | 'vertical' // 滚动方向
  initialSlide?: number // 滑块初始位置
  initialPassTime?: number // 指示器初始位置
}
function Carousel(props: Props) {
  const { speed = 3000, carouselList = [], initialSlide = 0, initialPassTime = 0 } = props
  if (!carouselList?.length) {
    throw 'empty array is not allowed!'
  }
  const [activeIndex, setActiveIndex] = useState(initialSlide)
  const [passTime, setPasssTime] = useState(initialPassTime)
  useEffect(() => {
    const now = Date.now()
    const timer = setTimeout(() => {
      const _passTime = passTime + Date.now() - now
      if (_passTime < speed) {
        setPasssTime(_passTime)
      } else {
        setPasssTime(0)
        setActiveIndex((activeIndex + 1) % carouselList.length)
      }
    }, 16)
    return () => clearTimeout(timer)
  }, [activeIndex, passTime, carouselList, speed])
  return (
    <div className="swiper-container">
      <div
        className="swiper-wrapper"
        style={{ width: 100 * carouselList.length + '%', left: -100 * activeIndex + '%' }}
      >
        {carouselList?.map((c, index) => {
          return (
            <div key={`swiper_${index}`} className="swiper-item" style={{ backgroundImage: `url(${c.bgUrl})` }}>
              {c.title?.split('\n').map(t => {
                return (
                  <div key={t} className="title" style={{ color: c.titleColor || '#333333' }}>
                    {t}
                  </div>
                )
              })}
              {c.subTitle?.split('\n').map(s => {
                return (
                  <div key={s} className="text" style={{ color: c.subTitleColor || '#333333' }}>
                    {s}
                  </div>
                )
              })}
            </div>
          )
        })}
      </div>
      <div className="anchors-wrap">
        {carouselList?.map((item, index) => (
          <div className="anchors-wrap_item" key={`anchors_${index}`}>
            <div
              className="anchors-wrap_anchor"
              style={{ width: activeIndex === index ? `${((passTime / speed) * 100).toFixed(2)}%` : '0%' }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  )
}
export default Carousel
