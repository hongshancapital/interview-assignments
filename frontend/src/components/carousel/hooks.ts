import { useRef, useCallback, useEffect } from 'react'

export function useAtuoPlay({
  updateActiveIndex,
  autoplaySpeed,
  autoplay,
}: {
  autoplaySpeed: number
  updateActiveIndex: () => void
  autoplay: boolean
}) {
  const stopplayFnRef = useRef<((...arg: any) => void) | null>(null)
  const autoplayFn = useCallback(() => {
    stopplayFnRef.current && stopplayFnRef.current()

    const timmer = setInterval(updateActiveIndex, autoplaySpeed)
    stopplayFnRef.current = () => {
      if (timmer) {
        clearInterval(timmer)
      }
    }
    return stopplayFnRef.current
  }, [autoplaySpeed, updateActiveIndex])
  useEffect(() => {
    if (!autoplay) {
      return
    }
    return autoplayFn()
  }, [autoplayFn, autoplay])
  return {
    autoplayFn,
    stopplayFn: () => stopplayFnRef.current && stopplayFnRef.current(),
  }
}
