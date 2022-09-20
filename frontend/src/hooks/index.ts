import { useCallback, useEffect, useLayoutEffect, useRef, useState } from "react"

export function useInterval(callback: () => void, delay: number) {
  const callbackRef = useRef(callback)

  useLayoutEffect(() => {
    callbackRef.current = callback
  }, [callback])

  useEffect(() => {
    if (!delay) return

    const timer = setInterval(() => callbackRef.current(), delay)

    return () => clearInterval(timer)
  }, [delay])
}

export function useCounter(initialValue?: number) {
  const [count, setCount] = useState(initialValue || 0)
  const increment = () => setCount(x => x + 1)
  const decrement = () => setCount(x => x - 1)
  return {
    count,
    setCount,
    increment,
    decrement,
  }
}

export function useCircularCounter(max: number, initialValue?: number) {
  const { count, ...rest } = useCounter(initialValue || 0)
  let newCount = count % max
  if (count < 0) newCount += max
  return {
    count: newCount,
    ...rest,
  }
}


export function useResize(callback: () => void) {
  useLayoutEffect(() => {
    window.addEventListener('resize', callback)
    return () => {
      window.removeEventListener('resize', callback)
    }
  }, [callback])
}

interface Size {
  width: number
  height: number
}
export function useSize<T extends HTMLElement = HTMLDivElement>(): [React.MutableRefObject<T | null>, Size] {
  const elementRef = useRef<T | null>(null)
  const [size, setSize] = useState({ width: 0, height: 0 })

  const onResize = useCallback(() => {
    const width = elementRef.current?.offsetWidth || 0
    const height = elementRef.current?.offsetHeight || 0

    setSize({ width, height })
  }, [])

  useResize(onResize)

  useLayoutEffect(() => {
    onResize()
  }, [onResize])

  return [elementRef, size]
}
