import React from 'react'
import './index.css'
import { useCarousel } from './useCarousel'

interface CarouselProps {
  autoplay?: boolean,
  delay?: number,
  children?: React.ReactNode
}
  
const Carousel: React.FC<CarouselProps> = ({
  autoplay = true,
  delay = 3000,
  ...props
}) => {
  const children = React.Children.toArray(props.children)
  const count = children.length

  const { activeIndex, setActiveIndex, progress, interval } = useCarousel({
    count,
    delay,
    autoplay
  })

  const containerStyle: React.CSSProperties = {
    transform: `translateX(-${ 100 * activeIndex }%)`
  }

  const maskStyle: (i: number) => React.CSSProperties | undefined = (i) => {
    return i === activeIndex ? { width: `${progress}%`} : undefined
  }

  return (
    <div 
      className='carousel'
      onMouseEnter={() => interval?.paused()}
      onMouseLeave={() => interval?.play()}
    >
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
                  style={maskStyle(i)}
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