import { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import './style.css'
import PageIndicator from './PageIndicator'

export interface ICarouselProps<T> {
  data: T[]
  renderItem?: (itemData: T, index: number) => React.ReactNode
  // 是否自动播放
  autoPlay?: boolean
  // 间隔多久切换，单位秒
  interval?: number
  // 切换速度，单位秒
  speed?: number
}

export default function Carousel<T>(props: ICarouselProps<T>) {
  const { data, renderItem, autoPlay = true, interval = 2, speed = 0.5 } = props
  const carouselRef = useRef<HTMLDivElement>(null)
  const [activeIndex, setActiveIndex] = useState(0)
  const timer = useRef<any>()
  const renderCarouselItem = useCallback((itemData: T, index: number) => {
    // 可以使用自定义的Render方法
    if (!renderItem) {
      return null
    }

    return renderItem(itemData, index)
  }, [])

  const initTimer = () => {
    timer.current = autoPlay
      ? setInterval(() => {
          setActiveIndex((prev) => {
            if (prev + 1 >= data.length) {
              return 0
            }
            return prev + 1
          })
        }, interval * 1000)
      : null
  }

  useEffect(() => {
    initTimer()

    return () => {
      if (timer.current) clearInterval(timer.current)
    }
  }, [])

  const onIndicatorClick = (index: number) => {
    // 计时到一半时点击，需要清除当前的计时，重新开始
    clearInterval(timer.current)
    initTimer()
    setActiveIndex(index)
  }

  return useMemo(() => {
    return (
      <div className="carousel" ref={carouselRef}>
        {data.map((item, index) => {
          return (
            <div
              className="carousel-scroller"
              style={{
                left: `${index * 100}vw`,
                transform: `translateX(${-1 * activeIndex * 100}%)`,
                transitionDuration: `${speed}s`,
              }}
              key={index}
            >
              {renderCarouselItem(item, index)}
            </div>
          )
        })}
        <div className="indicator-container">
          {data.map((_, index) => {
            return (
              <PageIndicator
                key={`pageIndicator-${index}`}
                onClick={() => onIndicatorClick(index)}
                activeIndex={activeIndex}
                interval={interval}
                index={index}
              ></PageIndicator>
            )
          })}
        </div>
      </div>
    )
  }, [data, renderItem, activeIndex, speed, interval])
}
