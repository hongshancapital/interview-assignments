import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react'
import {CarouselProps} from "./type";

const MaxDurationTime = 300

export const useCarouselLogic = (props: CarouselProps) => {
  const { duration = 0, children: originalChildren, showIndicator = true, defaultActive = 0} = props

  // 当前活跃下标
  const [activeIndex, setActiveIndex] = useState(defaultActive)
  // 真实轮播图所处活跃的下标（因为添加了一个copy后缀item）
  const actualActiveIndex = useRef(defaultActive)
  // 子代数目
  const childCount = useMemo(() => React.Children.count(originalChildren), [originalChildren])
  // 计算跳转到对应下标索引所需百分比位移
  const calcToIndexNeedPercentage = useCallback(
    (targetIndex: number) => `${-targetIndex * 100}%`,
    []
  )
  const [contentStyle, setContentStyle] = useState<React.CSSProperties>({
      transform: `translateX(${calcToIndexNeedPercentage(defaultActive)})`,
      transition: 'none'
    }
  )

  // 处理原始数组，在数组最后增加第一个子代，实现无缝链接功能
  const children = useMemo(() => {
    const list = React.Children.toArray(originalChildren)

    if(list.length === 0){
      return []
    }

    return [...list, [list[0]]]
  }, [originalChildren])

  // 内容过度动画参数配置
  const contentTransition = useMemo(() => {
    let transitionDuration = MaxDurationTime

    if(duration === 0){
      return 'none'
    }

    // 小于最大变化时间，则按比例计算动画时间，保证动画时间<变化周期
    if(duration < MaxDurationTime * 1.1){
      transitionDuration = Math.trunc(duration / 2)
    }

    return `all ${transitionDuration}ms ease-in-out`
  },[duration])

  const onContentTransitionEnd = useCallback(() => {
    if (actualActiveIndex.current === childCount) {
      setContentStyle({
        transform: `translateX(${calcToIndexNeedPercentage(0)})`,
        transition: 'none'
      })
      actualActiveIndex.current = 0
    }
  },[childCount, calcToIndexNeedPercentage])

  useEffect(() => {
    let handler = 0 as any
    if (duration && childCount > 0) {
      handler = setInterval(() => {
        const newIndex = activeIndex === childCount - 1 ? 0 : activeIndex + 1
        if (newIndex !== activeIndex) {
          actualActiveIndex.current = activeIndex + 1
          setContentStyle({
            transform: `translateX(${calcToIndexNeedPercentage(actualActiveIndex.current)})`,
            transition: contentTransition
          })
          setActiveIndex(newIndex)
        }
      }, duration)
    }
    return () => clearInterval(handler)
  }, [activeIndex, childCount, duration, contentTransition, calcToIndexNeedPercentage])

  return {
    children,
    showIndicator,
    activeIndex,
    contentStyle,
    onContentTransitionEnd
  }
}