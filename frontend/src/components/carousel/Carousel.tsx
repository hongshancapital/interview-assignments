import React, { useEffect, useState } from 'react'
import './carousel.scss'

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
  const [progress, setProgress] = useState(0)
  const [slideOut, setSlideOut] = useState(-1)

  const interval = props.interval || 2000

  useEffect(() => {
    let num = 0
    const timer = setInterval(() => {
      num++
      setProgress((p) => (p + 1) % 100)
      if (num > 100) {
        setProgress(100)
        clearInterval(timer)
      }
    }, interval / 100)

    return () => {
      clearInterval(timer)
    }
  }, [active])

  useEffect(() => {
    const timer = setTimeout(() => {
      let next = (active + 1) % props.data.length
      setTimeout(() => {
        setSlideOut(active)
        setActive(next)
      }, props.duration || 500)
    }, interval)

    return () => {
      clearTimeout(timer)
    }
  }, [active, interval])

  const CarouselItem = (item: CarouselItem, index: number) => {
    const style = {
      backgroundImage: `url(${item.img})`,
      color: item.color || '#000',
      backgroundColor: item.bgColor || 'inherit',
      transitionDuration: `${props.duration || 500}ms`,
      animationName: index === active ? 'slideIn' : 'slideOut'
    }
    const classArr = ['carouse-item']
    if (index === active) {
      classArr.push('active')
    }
    return <div className={classArr.join(' ')} style={style} key={index}>
      <div className="title">{typeof item.title === 'string' ? item.title : item.title.map((v: string) => <div key={v}>{v}</div>)}</div>
      {item.desc && <div className="desc">{typeof item.desc === 'string' ? item.desc : item.desc.map((v: string) => <div key={v}>{v}</div>)}</div>}
    </div>
  }

  const CarouselIndicator = (index: number) => {
    return <div className="indicator-item" key={index}>
      <div className="progress" style={{ width: active === index ? `${progress}%` : '0%' }}></div>
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