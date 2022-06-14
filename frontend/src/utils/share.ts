import { useRef } from 'react';

export const noop = () => {}

export const useOnce = <R>(fn: () => R): R => {
  const ref = useRef<R>();
  if (!ref.current) {
    ref.current = fn()
  }
  return ref.current
}
