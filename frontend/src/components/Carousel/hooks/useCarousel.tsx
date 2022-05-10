import React, { useCallback, useState, useEffect, useRef } from 'react'
import { Options, CarouselProps } from '../Options'

function useCarousel(props: CarouselProps): {
  index: number,
  total: number,
  data: Array<Options>,
  duration: number,
  refWrap: React.RefObject<HTMLDivElement>,
  onTransitionEnd: React.TransitionEventHandler<HTMLDivElement>
} {
  const { data, duration = 9 } = props
  const total = data.length  // 轮播图总数

  // 记录当前轮播图的索引，后面根据这个值来计算panel-wrap元素偏移量（使用百分比）
  const [index, setIndex] = useState<number>(0)
  const ref = useRef<HTMLDivElement>(null)

  // 设置底部指示器active索引，触发Slider过渡动画
  const setActiveIndex = (index: number) => {
    let curIndex = index
    // 如果轮播到尾部，则马上回到头部，并且开始新的一轮循环
    const isEnd = index >= total
    if (isEnd) {
      curIndex = 0
    }
    curIndex++
    setIndex(curIndex)
  }

  // 根据当前索引设置下一个轮播图的偏移量
  const setTranslateX = (curIndex: number) => {
    const offsetX = curIndex === total ? '0%' : `${(- curIndex) * 100 / total}%`
    const { style } = ref.current || {}

    if (style) {
      style.transform = `translateX(${offsetX})`
    }
  }

  // 开始轮播
  const run = (index: number) => {
    if (!ref.current || total <= 1) {
      return
    }
    // 设置容器宽度，容器的宽度 = 帧宽 * 帧数，使用百分比计算
    ref.current.style.width = `${total * 100}%`
    setActiveIndex(index)
  }

  // 使用onTransitionEnd，在Slider过渡动画结束后开始切换轮播图
  const onTransitionEnd = useCallback(() => {
    setTranslateX(index)
    // 由于轮播duration时长设置为0.5s，所以为了保持整体动作一致，500ms后开始新的轮播过程
    setTimeout(() => {
      requestAnimationFrame(() => run(index))
    }, 500)
  }, [index]) // eslint-disable-line

  // 页面初始化，开始执行轮播
  useEffect(() => {
    run(0)
  }, [data]) // eslint-disable-line

  return {
    index,
    total,
    data,
    duration,
    refWrap: ref,
    onTransitionEnd
  }
}

export default useCarousel
