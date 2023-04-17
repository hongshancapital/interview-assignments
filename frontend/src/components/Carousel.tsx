import React, { CSSProperties, ReactNode, useState, useEffect, useRef, useMemo, useCallback } from 'react'
import classnames from 'classnames'
import { times } from 'lodash-es'
import './Carousel.css'

interface IProps {
  className?: string
  style?: CSSProperties
  /** 是否自动播放 */
  autoPaly?: boolean
  /** 轮播列表 */
  children?: ReactNode
  /** 间隔时间 */
  duration?: number
}

export default function Carousel(props: IProps) {
  const { className, style, autoPaly = true, children, duration = 3000 } = props
  const [activeIndex, setActiveIndex] = useState<number>(0)
  const autoPlayIntervalRef = useRef<any>(null)
  const childrenCount = useMemo(() => React.Children.count(children), [children])

  const next = useCallback(() => {
    setActiveIndex(activeIndex => (activeIndex + 1) % childrenCount)
  }, [childrenCount])

  const stop = () => {
    clearInterval(autoPlayIntervalRef.current)
  }

  const autoPlay = useCallback(() => {
    stop()
    autoPlayIntervalRef.current = setInterval(next, duration)
  }, [duration, next])
  

  useEffect(() => {
    if (autoPaly) {
      autoPlay()
    }

    return () => {
      clearInterval(autoPlayIntervalRef.current)
    }
  }, [autoPaly, autoPlay, children])

  // 点击对应进度条跳转至对应banner
  const turnTo = useCallback((index: number) => () => {
    setActiveIndex(index)
  }, [])

  const cls = classnames('c-carousel', className)
  const carouselSliderStyle: CSSProperties = {
    transform: `translateX(-${activeIndex * 100}%)`
  }
  const carouselItemStyle: CSSProperties = {
    width: style?.width
  }
  return (
    <div className={cls} style={style}>
      {/* 轮播图部分 */}
      <div className="c-carousel-slider" style={carouselSliderStyle}>
        {
          React.Children.map(children, (child, index) => {
            return (
              <div className="c-carousel-item" style={carouselItemStyle} key={index}>
                {child}
              </div>
            )
          })
        }
      </div>
      {/* 进度条部分 */}
      <div className="c-carousel-controllers">
        {
          times(childrenCount, index => {
            const cls = classnames(
              'c-carousel-controller',
            )
            return (
              <div className={cls} key={index} onClick={turnTo(index)}>
                <div style={{ ['--duration' as any]: `${duration / 1000}s` }} className={classnames('c-carousel-controller-item', {
                  'c-carousel-controller-item--active': index === activeIndex
                })} />
              </div>
            )
          })
        }
      </div>
    </div>
  )
}
