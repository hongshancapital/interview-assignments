import { useEffect, useState } from 'react'
interface cb {
  (msTime: number): void
}

export function useInterval(callback: cb, interval: number) {
  useEffect(() => {
    const start = new Date().getTime()
    const t = setInterval(() => {
      callback(new Date().getTime() - start)
    }, interval)
    return () => clearInterval(t)
  }, [])
}

export function useSliderIndex(bannerLength: number, speed: number = 3000) {
  const [focusIndex, setSliderIndex] = useState(0)
  useInterval((msTime: number) => {
    setSliderIndex(() => Math.floor(msTime / speed) % bannerLength)
  }, 300)
  return focusIndex
}