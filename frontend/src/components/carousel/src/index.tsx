import { FC, useEffect, useState, useRef, CSSProperties } from 'react'
import { useRafInterval } from 'ahooks'

import CarouselItem from './item'

import type { CarouselProps } from './typing'

import './index.scss'
import classnames from 'classnames'

type CarouselSwitcherProps = {
  duration: number
  showProgress: boolean
}

const CarouselSwitcher: FC<CarouselSwitcherProps> = () => {
  return <></>
}

const Carousel: FC<CarouselProps> = ({ autoPlay = true, duration = 3000, showProgress = true, items }) => {
  const SCREEN_WIDTH = window.screen.width
  const contentContainer = useRef(null)

  const _duration = !duration || duration <= 0 ? 3000 : duration

  const carouselContentStyle: CSSProperties = {
    transitionDuration: `1s`,
    width: `${(items?.length || 0) * SCREEN_WIDTH}px`,
  }
  const progressAnimStyle: CSSProperties = {
    animation: `loadProgress ${Math.floor(_duration / 1000)}s linear infinite`,
  }

  const [activeIdx, setActiveIdx] = useState(0)

  useEffect(() => {
    setTransition()
  }, [activeIdx])

  const setTransition = () => {
    const distance = (0 - activeIdx) * SCREEN_WIDTH
    if (contentContainer.current) {
      const el = contentContainer.current as HTMLElement

      el.style.transform = `translate3d(${distance}px, 0, 0)`
    }
  }

  let clearTimer = useRafInterval(() => {
    setActiveIdx(activeIdx === (items?.length || 0) - 1 ? 0 : activeIdx + 1)
  }, duration)

  if (!autoPlay) {
    clearTimer()
  }

  useEffect(() => {
    return () => {
      clearTimer && clearTimer()
    }
  }, [])

  if (!items || !Array.isArray(items) || !items.length) {
    console.warn('carousel items prop can not be empty.')
    return <></>
  }

  return (
    <div className="carousel-container">
      <CarouselSwitcher duration={_duration} showProgress={showProgress} />
      <div ref={contentContainer} className="carousel-content" style={carouselContentStyle}>
        {items.map((item, idx) => (
          <CarouselItem key={`carousel-item-${item.title}-${idx}`} {...item} active={activeIdx === idx} idx={idx} />
        ))}
      </div>

      <div className="dots">
        {items.map((_, idx) => {
          if (activeIdx === idx) {
            return (
              <div className={classnames('dot', { active: !showProgress })}>
                {showProgress && <div className="progress" style={progressAnimStyle}></div>}
              </div>
            )
          }

          return <div data-idx={idx} className={classnames('dot')}></div>
        })}
      </div>
    </div>
  )
}

export default Carousel
