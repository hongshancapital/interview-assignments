import { useRef } from 'react'

const useRefs = <T>(): [
  typeof refs.current,
  (index: number) => (el: T) => void
] => {
  const refs = useRef<T[]>([])

  const setRefs = (index: number) => (el: T) => {
    refs.current[index] = el
  }

  return [refs.current, setRefs]
}

export default useRefs
