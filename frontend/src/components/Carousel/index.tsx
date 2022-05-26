import React, { FunctionComponent, PropsWithChildren, ForwardRefRenderFunction, useCallback, useState, useMemo, useRef, useEffect, useImperativeHandle, forwardRef } from 'react'
import { TCarouselProps, TCarouselFun, TCarouselItemProps } from './types'
import useTransitionEvent from '../../hooks/useTransitionEvent'
import useInterval from '../../hooks/useInterval'

import './index.scss'

const c = (prefix?: string) => `carousel${prefix ? `-${prefix}` : ''}`

const carouselItemKey = c('item')

const Carousel: ForwardRefRenderFunction<TCarouselFun, PropsWithChildren<TCarouselProps>> = (props, ref) => {
  const { children, autoPlay = false, duration = 2000, onTransitionStart, onTransitionEnd } = props
  const $wrapperRef = useRef<HTMLDivElement>(null)
  const [currentIndex, setCurrentIndex] = useState(0)
  const [targetIndex, setTargetIndex] = useState(0)
  const [autoPlayDuration, setAutoPlayDuration] = useState(0)
  // 过滤正确的组件
  const carouselChildren = useMemo(() => React.Children.map(children, (child: any) => child?.type?.displayName === carouselItemKey ? child : null)?.filter(d => d) || [], [children])
  // 定时任务
  useInterval(() => {
    onToTarget(currentIndex + 1)
  }, autoPlayDuration)
  const carouselChildrenLength = carouselChildren.length
  // 暂停
  const parse = useCallback(() => {
    autoPlay && setAutoPlayDuration(0)
  }, [autoPlay])
  // 开始
  const start = useCallback(() => {
    autoPlay && setAutoPlayDuration(duration)
  }, [autoPlay, duration])
  // 动画执行
  useTransitionEvent($wrapperRef, {
    onTransitionStart: () => {
      // 动画开始
      onTransitionStart && onTransitionStart(currentIndex, targetIndex)
    },
    onTransitionEnd: () => {
      // 动画结束
      onTransitionEnd && onTransitionEnd(currentIndex, targetIndex)
      setCurrentIndex(targetIndex)
      start()
    },
  }, [currentIndex, targetIndex])
  // 暴露功能
  useImperativeHandle(ref, () => ({
    jump: onToTarget,
    parse,
    start
  }))
  // 循环
  useEffect(() => {
    start()
  }, [])
  // 组件渲染
  const renderComponents = () => carouselChildren.map((d, index) => {
    // 性能优化 不在当前屏幕的元素滚动的时候显示，动画完毕的时候隐藏
    let style = {
      transform: `translateX(${100 * index}%)`,
      width: `${100 / carouselChildrenLength}%`
    }
    let [from, to] = [currentIndex, targetIndex]
    if (targetIndex < currentIndex) {
      [from, to] = [targetIndex, currentIndex]
    }
    if (from <= index && index <= to) {
      style = Object.assign({}, style, {
        opacity: '1',
        display: 'block'
      })
    }
    return React.cloneElement(d, { style })
  })
  // 跳转
  const onToTarget = useCallback((index: number) => {
    const isLast = index === carouselChildrenLength
    const isFirst = index === -1
    if (isLast) {
      setTargetIndex(0)
    } else if (isFirst) {
      setTargetIndex(carouselChildrenLength - 1)
    } else {
      setTargetIndex(index)
    }
    parse()
  }, [carouselChildrenLength, parse])
  const innerStyle = {
    width: `${100 * carouselChildrenLength}%`,
    transform: `translateX(${100 / carouselChildrenLength * - targetIndex}%)`
  }
  return <div className={c()}>
    <div className={c('canvas')}>
      <div className={c('canvas-inner')} ref={$wrapperRef} style={innerStyle}>{renderComponents()}</div>
    </div>
    <div className={c('action prev')} onClick={onToTarget.bind(null, currentIndex - 1)}>prev</div>
    <div className={c('action next')} onClick={onToTarget.bind(null, currentIndex + 1)}>next</div>
    <ul className={c('dot-list')}>{carouselChildren.map((d, index) => <li key={index} className={index === targetIndex ? 'active' : ''} onClick={onToTarget.bind(null, index)} />)}</ul>
  </div>
}
export default forwardRef(Carousel)

export const CarouselItem: FunctionComponent<TCarouselItemProps> = (props) => {
  const { children, style } = props
  return <div style={style} className={c('item-wrapper')}>{children}</div>
}
CarouselItem.displayName = carouselItemKey