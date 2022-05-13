import React, { useCallback, useState, useEffect } from 'react'
import { Options, CarouselProps } from '../Options'

function useCarousel(props: CarouselProps): {
  index: number,
  total: number,
  data: Array<Options>,
  duration: number,
  onTransitionEnd: React.TransitionEventHandler<HTMLDivElement>
} {
  const { data, duration = 9 } = props
  const total = data.length  // 轮播图总数

  // 记录当前轮播图的索引，后面根据这个值来计算panel-wrap元素偏移量（使用百分比）
  const [index, setIndex] = useState<number>(0)

  // 使用onTransitionEnd，设置index值并将其加入依赖列表实现轮播循环
  const onTransitionEnd = useCallback(() => {
    // 计算下一个active的指示器下标，取值范围：[1, total]，轮播到尾部时index变为1，回到开始位置重新播放
    const curIndex = index % total + 1
    setIndex(curIndex)
  }, [index, total])

  useEffect(() => {
    setIndex(1)
  }, [])

  return {
    index,
    total,
    data,
    duration,
    onTransitionEnd
  }
}

export default useCarousel
