import React, { CSSProperties, useState } from 'react'
import Indicator from './indicator'
import './style.css'

export interface CarouselProps {
  // 每一帧的停留时间, 单位: 毫秒
  stayTime: number

  // 切换到下一帧时的动画效果时间, 单位: 毫秒
  animationTime: number

  // 帧列表
  items: React.ReactNode[]
}

function Carousel(props: CarouselProps) {
  const { stayTime, animationTime, items } = props
  const [whichIsActive, setWhichIsActive] = useState(0)
  const style = {
    '--left': `-${100 * whichIsActive}%`,
    '--animationTime': `${animationTime}ms`
  } as CSSProperties

  setTimeout(() => {
    const count = items.length
    const next = (whichIsActive + 1) % count
    setWhichIsActive(next)
  }, stayTime)

  return (
    <div className='carousel-container' style={style}>
      <div className='carousel-items'>
        { items.map((item, idx ) => <div key={idx} className='carousel-item'>{item}</div>) }
      </div>
      <div className='carousel-indicators'>
        { items.map((item, idx) => <Indicator key={idx} stayTime={stayTime} isActive={whichIsActive === idx} />)}
      </div>
    </div>
  )
}

export default Carousel