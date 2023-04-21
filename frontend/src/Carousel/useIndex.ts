import { useEffect, useRef, useState } from "react"


function useIndex(interval: number, length: number) {
  const [index, setIndex] = useState(0)
  const timer = useRef(0)
  useEffect(() => {
    timer.current = window.setInterval(() => {
      setIndex((pre) => {
        let current = pre % (length - 1)
        return current === 0 && pre !== 0 ? current : current + 1
      })
    }, interval)
  }, [length, interval])

  useEffect(() => {
    // unmount
    return () => {
      clearInterval(timer.current)
    }
  }, [])

  return index
}

export default useIndex