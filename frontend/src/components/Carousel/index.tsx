import React, { useEffect, useState } from 'react'
import './index.css'

interface CarouselProps {
  autoplay?: boolean,
  interval?: number,
  children?: React.ReactNode
}
  
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
    animationDuration: `${interval}ms`
  }

  useEffect(() => {
    const timer = setInterval(() => {
      setActiveIndex(val => val === count - 1 ? 0 : val + 1)
    }, interval)

    return () => clearInterval(timer)
  }, [interval, count])

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
                  className={`carousel-indicator-mask ${i === activeIndex ? 'active' : ''}`}
                  style={i === activeIndex ? maskStyle : undefined}
                  // onAnimationEnd={() => autoplay ? setActiveIndex(i === count - 1 ? 0 : i + 1) : null}
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