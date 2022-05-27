import React, { useEffect, useMemo, useRef, useImperativeHandle, useCallback} from 'react'

import useRect from '../../hooks/useRect'
import useCarousel from './useCarousel'
import CarouselItem from './Item'
import Indicators from './Indicators'

import './Carousel.scss'

export type CarouselRef = {
  goTo: (slide: number, dontAnimate?: boolean) => void
  next: () => void
  prev: () => void,
  innerCurrent: number
}

type CarouselProps = {
  /* 是否自动切换 */
  autoPlay?: boolean
  /* 间隔多少秒切换  */
  interval?: number
  /* 动画过渡时间 （毫秒） */
  duration?: number
  children: React.ReactNode
}

const Carousel = React.forwardRef<CarouselRef, CarouselProps>((props, ref) => {
  const {
    autoPlay = true, 
    interval= 3000, 
    duration = 500, 
  } = props

  const playerTimer = useRef<ReturnType<typeof setTimeout> | null>(null)
  const count = useMemo(() => React.Children.count(props.children), [
    props.children
  ])
  // Carousel容器宽高信息
  const { size, container } = useRect<HTMLDivElement>()
  const scrollerStyle = {width: size.width * count }

  const {
    scrollerRef,
    goTo,
    next,
    prev,
    current
  } = useCarousel({count, duration, width: size.width})

  const onPlay = useCallback(() => {
    if (count <= 1 || !autoPlay) {
      return
    }
    playerTimer.current = setTimeout(() => {
      next()
    }, interval)
  }, [count, autoPlay, interval, next])

  const onPause = () => {
    playerTimer.current && clearTimeout(playerTimer.current)
    playerTimer.current = null
  }

  const onIndicatorChange = (index: number) => {
    goTo(index)
  }

  useEffect(() => {
    if(size.width) {
      onPlay()
    }
    return () => {
      onPause()
    }
  }, [current, size.width, onPlay])

  useImperativeHandle(ref, ()=> ({
    goTo,
    prev,
    next,
    innerCurrent: current
  }))

  return (<div ref={container} className="carousel-container">
    <div className="carousel-scroller" style={scrollerStyle} ref={scrollerRef}>
      {React.Children.map(props.children, (child) => {
        if (!React.isValidElement(child) || child.type !== CarouselItem) return null
        return child
      })}
    </div>
    <Indicators 
      autoPlay={autoPlay} 
      current={current} 
      count={count} 
      interval={interval}
      onChange={onIndicatorChange} />
  </div>)
})

export default Carousel