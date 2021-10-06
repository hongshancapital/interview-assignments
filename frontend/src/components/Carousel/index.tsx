import React, { useState, useCallback, useEffect, cloneElement, isValidElement, ReactNode, useMemo } from "react"

import './style.css'

//面板指示点位置类型
export type DotPosition = 'left' | 'right' | 'top' | 'bottom'

//面板切换动画类型
export type EffectType = 'slide' | 'fade'

//外部参数对象
export interface Props {
  autoplay?: boolean //是否自动播放
  dotPosition?: DotPosition //指示点位置
  effect?: EffectType //切换动画
  interval?: number //轮播间隔
  afterChange?: (current: number) => void //面板改变后回调函数
  beforeChange?: (from: number, to: number) => void //面板改变前回调函数
  children: ReactNode //子元素集合
}

export default function Carousel({
  autoplay,
  effect = 'slide',
  interval = 3000,
  children,
  dotPosition = 'bottom',
  beforeChange,
  afterChange,
}: Props) {
  const [position, setPosition] = useState({ prev: 0, cur: 0 })

  const cnt = useMemo(() =>
  React.Children.map(children, (it: ReactNode, index) => {
      if (!isValidElement(it)) {
        console.warn('Carousel children must be an valid element')
        return null
      }
      const classNames = [it.props.className, 'carousel-item'].join(' ').trim()
      const styles = { ...it.props.style, transform: `translate3d(${(index - position.cur) * 100}vw, 0px, 0px)` }
      return cloneElement(it, { className: classNames, style: styles })
    })
    , [children, position, effect,])

  const count = cnt?.length ?? 0

  useEffect(() => {
    if (!autoplay) {
      return;
    }
    const handle = window.setInterval(() => {
      setPosition(v => ({ prev: v.cur, cur: (v.cur + 1) % count }))
    }, interval)
    return () => window.clearInterval(handle)
  }, [autoplay, interval, count, position])

  const handleChange = useCallback(
    (value: number) => {
      beforeChange?.(position.cur, value)
      setPosition({ prev: position.cur, cur: value })
    },
    [position, beforeChange]
  )

  useEffect(() => {
    afterChange?.(position.cur)
  }, [position.cur, afterChange])

  if (count === 0) {
    throw new Error('Invalid Carousel Children')
  }

  return (
    <div className="carousel">
      <div className="carousel-inner">
        {cnt}
      </div>
      <CarouselDot
        value={position.cur}
        onChange={handleChange}
        position={dotPosition}
        dotCount={count}
        interval={interval}
      />
    </div>
  )
}

//指示点对象
interface CarouselDotProps {
  position?: DotPosition //位置
  dotCount: number //数量
  value: number //索引
  onChange: (value: number) => void, //指示点改变时回调函数
  interval: number //轮播间隔
}

function CarouselDot({
  value,
  onChange,
  dotCount,
  position = 'bottom',
  interval
}: CarouselDotProps) {

  const classNames = ['carousel-dot', `carousel-dot-${position}`].join(' ').trim()

  const cnt = []
  for (let i = 0; i < dotCount; i++) {
    const itemClassNames = ['carousel-dot-item'].join(' ').trim()
    cnt.push(
      <div key={i} className={itemClassNames} onClick={() => onChange(i)}>
        {i === value ? (<p className={'progress-bar'} style={{ animationDuration: `${interval}ms` }}></p>) : null}
      </div>
    )
  }

  return (
    <div className={classNames}>
      {cnt}
    </div>
  )
}
