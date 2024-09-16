import { useCallback, useEffect, useRef, useState } from 'react'

type Timer = ReturnType<typeof setInterval>
type updateFun = (arg: number) => void

export function useIndicator({
  timeDuration,
  count = 0,
  autoPlay,
}: {
  timeDuration: number;
  count?: number;
  autoPlay?: boolean;
}): [
    number, updateFun, updateFun,
  ] {
  const [index, setIndex] = useState(0)
  const [indicatorNum, setIndicatorNum] = useState(count)
  const timer = useRef<Timer | null>(null)
  const setIndexHandle = useCallback((val: number) => {
    if (val < count) {
      setIndex(index)
    }
  }, [setIndex])

  const clearTimer = useCallback(() => {
    if (timer.current) {
      clearInterval(timer.current)
      timer.current = null
    }
  }, [])
  useEffect(() => {
    if (!indicatorNum || !autoPlay) return
    clearTimer()
    timer.current = setInterval(() => {
      let nextIndex = index + 1
      if (nextIndex >= indicatorNum) {
        nextIndex = 0
      }
      setIndex(nextIndex)
    }, timeDuration)
    return clearTimer
  }, [indicatorNum, index, autoPlay, clearTimer])

  useEffect(() => {
    if (timer.current && !autoPlay) {
      clearTimer()
    }
  }, [autoPlay, clearTimer])
  return [index, setIndexHandle, setIndicatorNum]
}