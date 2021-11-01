/* eslint-disable react-hooks/exhaustive-deps */
import { RefObject, useCallback, useEffect, useMemo, useState } from 'react'

interface Props {
  containerRef: RefObject<HTMLElement>
  frames: JSX.Element[]
  options: Options
}

interface Options {
  duration: number
  threshold: number
  loop: boolean
  auto: boolean
  interval: number
}

type Direction = 'current' | 'prev' | 'next' | number

export default function useCarousel({ containerRef, frames, options }: Props) {
  const [mounted, setMounted] = useState(false)
  const [current, setCurrent] = useState(0)
  const [transitionDuration, setTransitionDuration] = useState(0)
  const [offsetX, setOffsetX] = useState(0)
  const [isProcessTransit, setisProcessTransit] = useState(false)

  useEffect(
    () => {
      !mounted && setMounted(true)
      current > frames.length - 1 && setCurrent(frames.length - 1)
      const timerId = options.auto && onInterval()
      return () => {
        timerId && clearInterval(timerId)
      }
    },
    [mounted, isProcessTransit, current, options, frames.length]
  )

  const containerFrame = useMemo(() => containerRef.current, [mounted])
  const isFirstPage = useMemo(() => current === 0, [current])
  const isLastPage = useMemo(() => current === frames.length - 1, [current, frames])
  const normalizeFrames = useMemo(() => [frames[frames.length - 1], ...frames, frames[0]], [frames, options])

  const containerStyle = useMemo(
    () => ({
      transform: `translateX(${offsetX}px)`,
      transitionDuration: `${transitionDuration}ms`,
      width: '100%',
      height: '100%'
    }),
    [offsetX, transitionDuration]
  )

  const framesStyle = useMemo(
    () =>
      normalizeFrames.map((_, i) => {
        const n = i - 1
        if (n === current) {
          return { transform: `translateX(${0})` }
        } else {
          return { transform: `translateX(${-100 * (current - n)}%)` }
        }
      }),
    [current, normalizeFrames]
  )

  const onTouchStart = () => undefined

  const onTouchMove = (distance: number) => {
    if (isProcessTransit) return
    setOffsetX(distance)
  }

  const onTouchEnd = (distance: number) => {
    const direction = decideTravelDirection(normalizeDistance(distance))
    onTransition(direction)
  }

  const onClickNav = (direction: Direction) => {
    onTransition(direction)
  }

  const onTransition = (direction: Direction) => {
    if (isProcessTransit) return
    onTransitonStart(direction)
    setTimeout(() => {
      onTransitonEnd(direction)
    }, options.duration)
  }

  const onTransitonStart = (direction: Direction) => {
    setOffsetX(getFixedOffsetX(direction))
    setTransitionDuration(options.duration)
    setisProcessTransit(true)
  }

  const onTransitonEnd = (direction: Direction) => {
    setOffsetX(0)
    setTransitionDuration(0)
    setisProcessTransit(false)
    setCurrent(prevCurrent => updateCurrent(prevCurrent, direction))
  }

  const onInterval = useCallback(() => setInterval(() => onTransition('next'), options.duration + options.interval), [
    options,
  ])

  const getFrameSize = () => {
    if (!containerFrame) return { w: 0, h: 0 }
    const { width, height } = window.getComputedStyle(containerFrame)
    const w = width ? parseFloat(width.split('px')[0]) : 0
    const h = height ? parseFloat(height.split('px')[0]) : 0
    return { w, h }
  }

  const getFixedOffsetX = (direction: Direction) => {
    const frameSize = getFrameSize()
    if (typeof direction === 'number') return (direction - current) * -frameSize.w
    switch (direction) {
      case 'current':
        return 0
      case 'prev':
        if (!options.loop && isFirstPage) return 0
        return frameSize.w
      case 'next':
        if (!options.loop && isLastPage) return 0
        return -frameSize.w
      default:
        throw new Error('not match direction')
    }
  }

  const updateCurrent = (prevCurrent: number, direction: Direction) => {
    if (typeof direction === 'number') return direction
    switch (direction) {
      case 'current':
        return prevCurrent
      case 'prev':
        if (!options.loop && isFirstPage) return prevCurrent
        if (options.loop && isFirstPage) return frames.length - 1
        return prevCurrent - 1
      case 'next':
        if (!options.loop && isLastPage) return prevCurrent
        if (options.loop && isLastPage) return 0
        return prevCurrent + 1
      default:
        throw new Error('not match direction')
    }
  }

  const decideTravelDirection = (distance: number) => (distance === 0 ? 'current' : distance > 0 ? 'prev' : 'next')
  const normalizeDistance = (distance: number) => (Math.abs(distance) >= options.threshold ? distance : 0)

  return {
    current,
    isFirstPage,
    isLastPage,
    onTouchStart,
    onTouchMove,
    onTouchEnd,
    onClickNav,
    containerStyle,
    framesStyle,
    normalizeFrames,
  }
}