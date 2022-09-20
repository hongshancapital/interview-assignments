import React, { forwardRef, useImperativeHandle } from 'react'
import { useCircularCounter, useInterval, useSize } from '../../hooks'
import './index.css'

export interface CarouselProps {
  initialIndex?: number
  auotPlay?: boolean
  children?: React.ReactNode
  delay?: number
  dots?: boolean
}

export interface CarouselRef {
  next: () => void
  prev: () => void
}

const Carousel = forwardRef<CarouselRef, CarouselProps>(({
  initialIndex = 0,
  auotPlay = false,
  delay = 4000,
  children,
}, ref) => {
  const renderChildren = React.Children.toArray(children).filter(Boolean)
  const length = renderChildren.length

  const { count: index, increment, decrement } = useCircularCounter(length, initialIndex)
  const [setRef, { width }] = useSize()

  useInterval(increment, auotPlay ? delay : 0)
  useImperativeHandle(ref, () => ({
    next: increment,
    prev: decrement,
  }))

  return <div className='carousel' ref={setRef}>
    <div className='carousel-list' style={{ left: -width * index }}>{
      React.Children.map(renderChildren, (child) => (
        <div className='carousel-item' style={{ width }}>{
          child
        }</div>
      ))
    }</div>
    <Dots count={length} current={index} duration={auotPlay ? delay / 1000 : 0} />
  </div>
})

Carousel.displayName = 'Carousel'

interface DotsProps {
  count: number
  current: number
  duration?: number
}
function Dots ({ count, current, duration }: DotsProps) {
  return <div className='dots'>{
    Array(count).fill(1).map((_e, idx) =>
      <div className={'dot' + (current === idx ? ' actived' : '')} key={idx}>
        <div className='dot-progress' style={{ animationDuration: `${duration}s` }}></div>
      </div>
    )
  }</div>
}

export default Carousel
