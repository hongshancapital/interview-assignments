import React, { useState } from 'react'
import './index.css'

interface CarouselProps {
  autoplay?: boolean,
  interval?: number,
  children?: React.ReactNode
}
  
/**
 * tip:
 * 为什么使用动画结束事件做自动播放，而不用定时器？
 * 主要是方便做鼠标悬浮暂停，指示器那里有个宽度变大的动画，
 * 如果使用定时器，那里在暂停和暂停恢复时就不好处理了。
 * 使用动画结束事件则可以利用动画的暂停css处理暂停，展示效果更好。
 */
const Carousel: React.FC<CarouselProps> = ({
  autoplay = true,
  interval = 3000,
  ...props
}) => {
  const [activeIndex, setActiveIndex] = useState(0)
  const children = React.Children.toArray(props.children)
  const count = children.length

  const containerStyle: React.CSSProperties = {
    transform: `translateX(-${ 100 * activeIndex }%)`
  }

  const maskStyle: React.CSSProperties = {
    animation: `indicator ${interval}ms linear`
  }

  return (
    <div className='carousel'>
      <div 
        className='carousel-container'
        style={containerStyle}
      >
        {
          children.map((child, i) => (
            <div
              className='carousel-item'
              key={i}
            >
              { child }
            </div>
          ))
        }
      </div>
      <div className='carousel-indicators'>
        {
          children.map((_, i) => (
            <div
              className='carousel-indicator'
              key={i}
              onClick={() => setActiveIndex(i)}
            >
              <div className='carousel-indicator-inner'>
                <div
                  className='carousel-indicator-mask'
                  style={i === activeIndex ? maskStyle : undefined}
                  onAnimationEnd={() => autoplay ? setActiveIndex(i === count - 1 ? 0 : i + 1) : null}
                />
              </div>
            </div>
          ))      
        }
      </div>
    </div>
  )
}

export default Carousel