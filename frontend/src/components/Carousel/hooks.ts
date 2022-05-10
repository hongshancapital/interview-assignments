import { useRef, useEffect } from 'react'

export function useInterval(callback: () => void, delay: number) {
  const intervalRef = useRef<number|null>(null)
  const savedCallback = useRef(callback)

  useEffect(() => {
    savedCallback.current = callback
  }, [callback])

  useEffect(() => {
    const func = () => savedCallback.current()

    intervalRef.current = window.setInterval(func, delay)

    return () => {
      intervalRef.current && window.clearInterval(intervalRef.current)
    }

  }, [delay])
}
