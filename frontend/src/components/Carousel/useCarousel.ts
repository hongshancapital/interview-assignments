import { useState, useEffect, useRef } from "react"
import Interval from './interval'

type UseCarouselParams = {
  delay: number,
  count: number,
  autoplay: boolean
}

export const useCarousel = ({ delay, count, autoplay }: UseCarouselParams) => {
  const [progress, setProgress] = useState(0)
  const [activeIndex, setActiveIndex] = useState(0)
  const intervalRef = useRef<Interval | null>(null)

  useEffect(() => {
    intervalRef.current = new Interval((_progress) => {
      setProgress(_progress)
      if (_progress === 100) {
        setActiveIndex(val => val === count - 1 ? 0 : val + 1)
      }
    }, delay)

    if (autoplay) intervalRef.current.play()

    return () => intervalRef.current!.paused()
  }, [delay, count, autoplay])

  const _setActiveIndex = (index: number) => {
    intervalRef.current?.reset()
    setActiveIndex(index)
  }

  return {
    progress,
    activeIndex,
    setActiveIndex: _setActiveIndex,
    interval: intervalRef.current
  }
}