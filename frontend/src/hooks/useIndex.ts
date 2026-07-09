import { useEffect, useState } from "react"

const useIndex = (length:number): number => {
  let [curIndex, setCurIndex] = useState(0)

  useEffect(() => {
    let timerId = setTimeout(() => {
      if(curIndex === length - 1) {
        setCurIndex(0)
      } else {
        setCurIndex(pre => ++pre)
      }
    }, 2000)
    return () => clearTimeout(timerId)
  }, [curIndex, length])

  return curIndex
}

export default useIndex