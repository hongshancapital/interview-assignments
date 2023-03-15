import { type FC, type ReactNode, type CSSProperties, useEffect, useState } from 'react'
import './index.css'

const randomStr = Math.random().toString(36).slice(2)

const classnames = (...arr: string[]) => {
  return arr.filter(Boolean).join(' ')
}

export interface CarouselProps {
  items: Array<{
    key: string
    render: () => ReactNode
  }>
  // autoplay delay
  delay?: number
}

const Carousel: FC<CarouselProps> = ({ items, delay = 3500 }) => {
  const [activeIndex, setActiveIndex] = useState(0)

  useEffect(() => {
    // Try to fix the `activeIndex` if `items` changes
    if (activeIndex >= items.length) {
      setActiveIndex(0)
      return
    }
    const timer = window.setTimeout(() => setActiveIndex((activeIndex + 1) % items.length), delay)

    return () => window.clearTimeout(timer)
  }, [items, delay, activeIndex])

  return (
    <div
      className="carousel"
      style={{ '--indicator-animation-duration': `${delay}ms` } as CSSProperties}
    >
      <ul className="carousel-wrapper" style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
        {items.map((item, idx) => (
          <li
            key={item.key}
            data-testid={`carouse-item-${idx}`}
            className={classnames('carousel-item', idx === activeIndex ? 'active' : '')}
          >
            {item.render()}
          </li>
        ))}
      </ul>
      <ul className="indicators">
        {items.map((item, idx) => (
          <li
            key={item.key}
            data-testid={`indicator-${idx}`}
            className={classnames('indicator', idx === activeIndex ? 'active' : '')}
            onClick={() => setActiveIndex(idx)}
          >
            <span className="indicator__track">
              {/* Restart animation when `delay` changes */}
              <span key={`${item.key}${randomStr}${delay}`} className="indicator__bar" />
            </span>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default Carousel
