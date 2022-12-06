import React, { useEffect, useRef, useState } from 'react'
import './carousel.scss'

interface ICarouselItem {
  title: string
  subTitle?: string
  color: string
  img: string
  bgColor?: string
}

interface ICarouselProps {
  data: ICarouselItem[]
  interval?: number // 每张轮播图持续时间，默认2000毫秒
  duration?: number // 轮播图切换持续时间，默认500毫秒
}

// 定时器执行周期
const interval = 10

export default function Carousel(props: ICarouselProps) {
  const flag = useRef(true) // 轮播方向，默认向左轮播
  const len = useRef(props.data.length)
  const index = useRef(0)
  const [width, setWidth] = useState(0)
  const widthStep = useRef(100 * interval / (props.interval || 2000))
  const [left, setLeft] = useState(0)
  const leftStep = useRef(100 * interval / (props.duration || 500))

  useEffect(() => {
    const timer = setInterval(() => {
      if (flag.current) {
        // 指示块
        setWidth(width => {
          if (width >= 100) {
            index.current = (index.current + 1) % len.current
            flag.current = false
            return 0
          }
          return width + widthStep.current
        })
      } else {
        // 转场切换
        setLeft(left => {
          if (index.current === 0 && left >= 0) {
            // 一轮循环完毕，回到第一张
            flag.current = true
            return 0
          } else if (index.current && left <= -100 * index.current) {
            // 转场完毕
            flag.current = true
            return -100 * index.current
          }
          if (index.current === 0) {
            // 回退
            return left + 2 * leftStep.current
          }
          return left - leftStep.current
        })
      }
    }, interval)
    return () => clearInterval(timer)
  }, [])

  return (
    <div className='container'>
      <div className='carousel' style={{ left: left + 'vw' }}>
        {
          props.data.map(item => <div className='carousel-item' key={item.title} style={{
            backgroundImage: `url(${item.img})`, color: item.color, backgroundColor: item.bgColor || ''
          }}>
            <h1 dangerouslySetInnerHTML={{ __html: item.title }}></h1>
            {item.subTitle ? <p dangerouslySetInnerHTML={{ __html: item.subTitle }}></p> : null}
          </div>)
        }
      </div>
      <div className="indicator">
        {
          props.data.map((item, i) => <div className='indicator-item' key={item.title}>
            <div className="progress" style={{ width: index.current === i ? width + '%' : 0 }}></div>
          </div>)
        }
      </div>
    </div>
  )
}
