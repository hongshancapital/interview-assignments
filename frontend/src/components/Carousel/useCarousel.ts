import React, { useEffect, useMemo, useRef, useState } from 'react'

const DEFAULT_INTERVAL = 3000; // 默认执行时间间隔
const DEFAULT_WIDTH = window.screen.width; // 默认宽度
const DEFAULT_HEIGHT = window.screen.height; // 默认高度

export interface IProps {
  control?: boolean; // 是否展示左右控制器
  interval?: number; // 间隔时长
  versa?: boolean; // 是否是反方向播放
  indicator?: boolean; //是否拥有底部导航
  auto?: boolean; // 自动播放
  width?: number;
  height?: number;
  children?: JSX.Element | JSX.Element[];
}

// 处理子元素结构,保证以数组形式呈现
export function items(children?: JSX.Element | JSX.Element[]) {
  if (!children || !Array.isArray(children)) return []
  if (Array.isArray(children)) return children
  if (React.isValidElement(children)) return [children]
  return []
}

export function useCarousel(props: IProps) {
  const {
    auto = false,
    versa = false,
    interval = DEFAULT_INTERVAL,
    width = DEFAULT_WIDTH,
    height = DEFAULT_HEIGHT,
  } = props;
  const children = items(props.children)
  // 当前元素位置
  const [active, setActive] = useState(1)
  const [progress, setProgress] = useState(0) // 进度指示
  // 动画播放状态
  const [animating, setAnimating] = useState(false)
  const animatingTime = useRef<NodeJS.Timer>()
  // 轮播dom容器
  const container = useRef<HTMLDivElement>(null)

  const handlePrev = () => {
    if (!props.control || animating) return
    setAnimating(true)
    setProgress(active === 1 ? children.length - 1 : progress - 1)
    container.current?.style.setProperty('transition', 'all 1s ease')
    setActive(active - 1);
  }

  const handleNext = () => {
    if (!props.control || animating) return;
    const _children = container.current!.childNodes
    setAnimating(true)
    setProgress(active === _children.length - 2 ? 0 : progress + 1)
    container.current?.style.setProperty('transition', 'all 1s ease')
    setActive(active + 1);
  }

  // 动画控制器
  const animation = () => {
    const children = container.current!.childNodes
    clearAnimation()
    if (interval && auto && children.length >= 2) {
      animatingTime.current = setInterval(() => {
        //return versa ? handlePrev() : handleNext();
      }, interval);
    }
  }

  const clearAnimation = () => {
    clearInterval(animatingTime.current as NodeJS.Timer)
    // animatingTime.current = null as any
  }

  // 到具体某个元素
  const go = (index: number) => {
    if (animating) return
    container.current?.style.setProperty('transition', 'all 1s ease')
    setActive(index)
    setProgress(index - 1)
  }

  // 鼠标移入时暂停播放
  const hover = () => {
    clearAnimation()
  }

  // 鼠标移出时重启自动播放
  const out = () => {
    return auto && animation()
  }

  const transitionend = () => {
    const children = container.current!.childNodes
    if (active === children.length - 1) {
      container.current?.style.setProperty('transition', 'none')
      container.current?.style.setProperty('transform', `translate3d(${-width}px, 0, 0)`)
      setActive(1)
    }
    if (active === 0) {
      container.current?.style.setProperty('transition', 'none')
      container.current?.style.setProperty('transform', `translate3d(${(1 - children.length) * width} px, 0, 0)`)
      setActive(children.length - 2)
    }
    setAnimating(false)
    container.current?.removeEventListener('transitionend', transitionend, false)
  }

  useEffect(() => {
    const distance = (0 - active) * width;
    container.current?.style.setProperty('transform', `translate3d(${distance}px, 0, 0)`)
    container.current?.addEventListener('transitionend', transitionend, false)
    return () => {
      // eslint-disable-next-line react-hooks/exhaustive-deps
      container.current?.removeEventListener('transitionend', transitionend, false)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [container.current, active, animating])

  useEffect(() => {
    animation()
    return () => {
      clearAnimation()
      // @ts-ignore
      // container.current = null as any
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [active, animating, progress])

  return {
    active,
    width,
    height,
    container,
    interval,
    children,
    progress,
    handleNext,
    handlePrev,
    go
  }
}