/*
 * @Author: NoManPlay
 * @Date: 2022-10-09 17:29:53
 * @LastEditors: NoManPlay
 * @LastEditTime: 2022-10-09 18:06:16
 * @Description:
 */
import {useCallback, useEffect, useRef} from 'react'

interface IOptions {
  immediate?: boolean
}

function useInterval(fn: () => void, delay: number, options?: IOptions) {
  const immediate = options?.immediate
  const fnRef = useRef<() => void>()
  const timeRef = useRef<NodeJS.Timer>()

  fnRef.current = fn

  const func = useEffect(() => {
    if (typeof delay !== 'number' || delay < 0 || !fnRef.current) return
    if (immediate) {
      fnRef.current()
    }
    timeRef.current = setInterval(() => {
      if (!fnRef.current) return
      fnRef.current()
    }, delay)

    return () => {
      if (timeRef.current) clearInterval(timeRef.current)
    }
  }, [delay])

  let clear = useCallback(() => {
    if (timeRef.current) clearInterval(timeRef.current)
  }, [])

  return clear
}

export default useInterval
