import { useMemo, useRef } from "react"

type noop = (...args: any[]) => any

export function useMemoizedFn<T extends noop>(fn: T) {
  const fnRef = useRef<T>(fn)
  fnRef.current = useMemo(() => fn, [fn])

  const memoizedFn = useRef<T>()
  if (!memoizedFn.current) {
    memoizedFn.current = function (...args) {
      return fnRef.current(...args)
    } as T
  }

  return memoizedFn.current
}
