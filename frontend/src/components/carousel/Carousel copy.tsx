import React, { useEffect, useState } from 'react'
import './carousel.scss'
import { url } from 'inspector'

export interface CarouselItem {
  title: string | Array<string>
  desc?: string | Array<string>
  img: string
  color?: string
  bgColor?: string
}
interface CarouselProps {
  data: Array<CarouselItem>
  interval?: number // 每张轮播图持续时间，默认2000毫秒
  duration?: number // 轮播图切换持续时间，默认500毫秒
}

export default function Carousel(props: CarouselProps) {
  const [active, setActive] = useState(0)
  const [width, setWidth] = useState(0)

  useEffect(() => {
    const timer = setTimeout(() => {
      setActive((active + 1) % props.data.length)
    }, 1000)
    return () => {
      clearTimeout(timer)
    }
  }, [active])

  const CarouselItem = (item: CarouselItem, index: number) => {
    return <div className={`carouse-item${index === active ? ' active' : ''}`} style={{ backgroundImage: `url(${item.img})`, color: item.color || '#000', backgroundColor: item.bgColor || 'inherit' }} key={index}>
      <div className="title">{typeof item.title === 'string' ? item.title : item.title.map((v: string) => <div>{v}</div>)}</div>
      {item.desc && <div className="desc">{typeof item.desc === 'string' ? item.desc : item.desc.map((v: string) => <div>{v}</div>)}</div>}
    </div>
  }

  const CarouselIndicator = (index: number) => {
    return <div className="indicator-item" key={index}>
      <div className="progress" style={{ width: active === index ? width + '%' : 0 }}></div>
    </div>
  }

  return (
    <div className='carousel'>
      {props.data.map((item: CarouselItem, i: number) => CarouselItem(item, i))}
      <div className="indicator">
        {props.data.map((_: CarouselItem, i: number) => CarouselIndicator(i))}
      </div>
    </div>
  )
}
