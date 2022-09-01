import { FC, useEffect, useState, useRef, CSSProperties } from 'react'
import { useRafInterval } from 'ahooks'

import { useResizeWindow } from '@/hooks/useResizeWindow'

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
  const _duration = !duration || duration <= 0 ? 3000 : duration

  const { screenWidth } = useResizeWindow()
  const progressAnimStyle: CSSProperties = {
    animation: `loadProgress ${Math.floor(_duration / 1000)}s linear infinite`,
  }

  const contentContainer = useRef(null)
  const [activeIdx, setActiveIdx] = useState(0)
  const [transformDistance, setTransformDistance] = useState(0)
  useEffect(() => {
    setTransformDistance((0 - activeIdx) * screenWidth)
  }, [activeIdx, screenWidth])

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
      <div
        ref={contentContainer}
        className="carousel-content"
        style={{
          transform: `translate3d(${transformDistance}px, 0, 0)`,
          width: `${(items?.length || 0) * screenWidth}px`,
        }}
      >
        {items.map((item, idx) => (
          <CarouselItem key={`carousel-item-${item.title}-${idx}`} {...item} active={activeIdx === idx} idx={idx} />
        ))}
      </div>

      <div className="dots">
        {items.map((_, idx) => {
          if (activeIdx === idx) {
            return (
              <div key={`carousel-dot-${idx}`} data-idx={idx} className={classnames('dot', { active: !showProgress })}>
                {showProgress && <div className="progress" style={progressAnimStyle}></div>}
              </div>
            )
          }

          return <div key={`carousel-dot-${idx}`} data-idx={idx} className={classnames('dot')}></div>
        })}
      </div>
    </div>
  )
}

export default Carousel
