import { useCallback, useEffect, useMemo, useRef, useState } from "react"
import './index.css'

interface IProps {
  duration?: number
  autoPlay?: boolean
  children: JSX.Element[]
}

export default function Carousel({
  children = [],
  duration = 3000,
  autoPlay
}: IProps) {
  const timer = useRef<NodeJS.Timer>()
  const [activeId, setActive] = useState(-1)

  const len = useMemo(() => children.length, [children])

  const clearTimer = useCallback(() => {
    if (timer.current) {
      clearInterval(timer.current)
      timer.current = undefined
    }
  }, [])

  const slick = useCallback(() => {
    if (!autoPlay) return

    clearTimer()

    timer.current = setInterval(() => {
      setActive(index => {
        if (index === len - 1) return 0
        return index + 1
      })
    }, duration)
  }, [len, duration, autoPlay, clearTimer])

  const selectItem = useCallback((index: number) => {
    slick()
    setActive(index)
  }, [slick])

  useEffect(() => {
    slick()
    // 推迟到下一次绘制前触发，防止数据在一个渲染帧内完成更新，导致第一页指示器动画效果不生效
    requestAnimationFrame(() => setActive(0))

    return () => {
      clearTimer()
    }
  }, [slick, clearTimer])

  return (
    <div className="Carousel">
      <div
        className="Carousel__list"
        style={{
          width: `calc(100vw * ${len})`,
          transform: `translateX(-${100 * (activeId > 0 ? activeId : 0)}vw)`
        }}
      >
        {
          children.map((node, index) => (
            <div
              className="Carousel__item"
              key={index}
            >{node}</div>
          ))
        }
      </div>
      <div className="Carousel__indicator">
        {children.map((_, index) => (
          <div
            className="Carousel__indicatorItem"
            key={index}
            onClick={() => selectItem(index)}
          >
            <div
              className={`Carousel__progress${index === activeId ? ' Carousel__progress--active' : ''}`}
              style={{ transitionDuration: autoPlay && index === activeId ? `${duration}ms` : '0ms' }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  )
}