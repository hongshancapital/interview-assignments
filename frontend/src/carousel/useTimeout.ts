import { useRef, useEffect, useReducer } from 'react'

const INTERVAL = 10
type Callback = () => void

function progressReducer(
  state: number,
  action: { type: 'incre', value: number }
) {
  switch (action.type) {
    case 'incre':
      const progress = state + action.value
      return progress >= 1 ? 1 : progress
    default:
      throw new Error();
  }
}

export const useTimeout = (
  callback: Callback = () => { },
  time: number
) => {
  const [progress, dispatchIncre] = useReducer(progressReducer, 0)
  const savedCallback = useRef(callback)

  useEffect(() => {
    savedCallback.current = callback
  }, [callback])

  useEffect(() => {
    const fn = () => {
      savedCallback.current()
    }
    let timeoutId = setTimeout(() => {
      clearInterval(intervalId)
      fn()
    }, time)
    let intervalId = setInterval(() => dispatchIncre({ type: 'incre', value: INTERVAL / time }), INTERVAL)
    return () => {
      clearTimeout(timeoutId)
      clearInterval(intervalId)
    }
  }, [time])

  return progress
}
