import { useEffect, useState } from 'react'

import { ShowTimeType } from "../types"

const useCarousel = (size: number, showTime:ShowTimeType ) => {
  const [activeIndex, setActiveIndex] = useState(0)

  // 初始化启动定时器
  useEffect(() => {
    const interval = setInterval(() => {
      setActiveIndex(activeIndex >= size - 1 ? 0 : activeIndex + 1)
    }, showTime * 1000)

    return () => {
      clearInterval(interval)
    }
  }, [size, activeIndex, showTime])


  return {
    activeIndex,
    setActiveIndex
  }
}

export default useCarousel