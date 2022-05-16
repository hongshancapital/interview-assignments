import React, { useCallback, useState, useEffect } from 'react'
import { Options } from '../Options'

function useCarousel(
  data: Array<Options>
): {
  activeIndex: number,
  total: number,
  data: Array<Options>,
  onTransitionEnd: React.TransitionEventHandler<HTMLDivElement>
} {
  const total = data.length  // 轮播图总数

  // 记录当前轮播图的索引，后面根据这个值来计算panel-wrap元素偏移量（使用百分比）
  const [activeIndex, setActiveIndex] = useState<number>(0)

  // 使用onTransitionEnd，设置index值并将其加入依赖列表实现轮播循环
  const onTransitionEnd = useCallback(() => {
    // 计算下一个active的指示器下标，取值范围：[1, total]，轮播到尾部时index变为1，回到开始位置重新播放
    const curIndex = activeIndex % total + 1
    setActiveIndex(curIndex)
  }, [activeIndex, total])

  useEffect(() => {
    setActiveIndex(1)
  }, [])

  return {
    activeIndex,
    total,
    data,
    onTransitionEnd
  }
}

export default useCarousel
