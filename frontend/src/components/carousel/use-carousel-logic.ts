import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react'
import {CarouselProps} from "./type";

const MaxDurationTime = 300

export const useCarouselLogic = (props: CarouselProps) => {
  const { className, style, duration = 0, children: originalChildren, showIndicator = true, defaultActive = 0} = props

  // 当前活跃下标
  const [activeIndex, setActiveIndex] = useState(defaultActive)
  // 子代数目
  const childCount = useMemo(() => React.Children.count(originalChildren || []), [originalChildren])
  // 内容（滚动容器）引用
  const contentRef = useRef<HTMLDivElement | null>(null)

  // 处理原始数组，在数组最后增加第一个子代，实现无缝链接功能
  const children = useMemo(() => {
    const list = React.Children.toArray(originalChildren)

    if(list.length === 0){
      return []
    }

    return [...list, [list[0]]]
  }, [originalChildren])

  // 计算跳转到对应下标索引所需百分比位移
  const calcToIndexNeedPercentage = useCallback(
    (targetIndex: number) => `${-targetIndex * 100}%`,
    []
  )

  // 内容动画配置
  const contentTransition = useMemo(() => {
    let transitionDuration = MaxDurationTime
    // 小于最大变化时间，则按比例计算动画时间，保证动画时间<变化周期
    if(duration < MaxDurationTime * 1.1){
      transitionDuration = Math.trunc(duration / 2)
    }

    return `all ${transitionDuration}ms ease-in-out`
  },[duration])

  // 此处 leftToRight 标识参数留作后续拓展 指示器点击功能
  // 此处使用 ref 而非 state，目的是为了便于做无缝循环切换
  const toIndex = useCallback(
    (newIndex: number, leftToRight: boolean) => {
      if (newIndex === activeIndex) {
        return
      }

      if (contentRef.current) {
        // 新下标为0，并且希望从左往右，此情况为：已经到了最后一个item，点击了next按钮操作，或者自动跳转到下一个
        // 跳转到默认额外添加的第一个item copy上
        if (newIndex === 0 && leftToRight) {
          contentRef.current.style.transform = `translateX(${calcToIndexNeedPercentage(
            childCount
          )})`
        }
        // 旧下标为0，并且希望从左往右，此时，我们需要保证，我们的 content 位于的是第一个 item，而非 copy 上
        else if (activeIndex === 0 && leftToRight) {
          // 禁用动画，先执行一次重定位
          contentRef.current.style.transition = 'none'
          contentRef.current.style.transform = `translateX(${calcToIndexNeedPercentage(0)})`
          // 等待下一帧，渲染完成，我们恢复动画，并且跳转到对应位置
          setTimeout(() => {
            if (contentRef.current) {
              contentRef.current.style.transition = contentTransition
              contentRef.current.style.transform = `translateX(${calcToIndexNeedPercentage(
                newIndex
              )})`
            }
          }, 0)
        }
          // 旧下标为0，并且希望从右往左，此情况为：已经到了最开始一个item，点击了last按钮操作
        // 我们需要保证，content 位于 第一个 item copy 上，然后执行跳转
        else if (activeIndex === 0 && !leftToRight) {
          // 禁用动画，先执行一次重定位
          contentRef.current.style.transition = 'none'
          contentRef.current.style.transform = `translateX(${calcToIndexNeedPercentage(
            childCount
          )})`
          // 等待下一帧，渲染完成，我们恢复动画，并且跳转到对应位置
          setTimeout(() => {
            if (contentRef.current) {
              contentRef.current.style.transition = contentTransition
              contentRef.current.style.transform = `translateX(${calcToIndexNeedPercentage(
                newIndex
              )})`
            }
          })
        } else {
          contentRef.current.style.transform = `translateX(${calcToIndexNeedPercentage(newIndex)})`
        }
      }

      setActiveIndex(newIndex)
    },
    [activeIndex, calcToIndexNeedPercentage, childCount, contentTransition]
  )

  useEffect(() => {
    let handler = 0 as any
    if (duration && childCount > 0) {
      handler = setInterval(() => {
        toIndex(activeIndex === childCount - 1 ? 0 : activeIndex + 1, true)
      }, duration)
    }
    return () => clearInterval(handler)
  }, [activeIndex, childCount, duration, toIndex])

  return {
    children,
    showIndicator,
    className,
    style,
    activeIndex,
    contentRef
  }
}