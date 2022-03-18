/**
 * this is a carousel component
 * author: ichenzhangdong@foxmail.com
 */
import React, { Children, useEffect, useRef, useState } from 'react'

import Dots from './Dots'
import { INIT_INDEX, DEFAULT_DURATION, prefixCls } from './constants'
import { ICarouselProps } from './types'
import './index.css'

let timer: null | ReturnType<typeof setTimeout> = null

export default function Carousel({
  duration = DEFAULT_DURATION,
  children
}: ICarouselProps) {
  const [currIndex, setCurrIndex] = useState(-1)
  const carouselRef = useRef<HTMLDivElement | null>(null)

  const childCount = Children.count(children)

  const handleLoadCarousel = () => {
    timer && clearTimeout(timer)
    timer = setTimeout(() => {
      setCurrIndex(idx => (childCount > idx + 1 ? idx + 1 : 0))
      handleLoadCarousel()
    }, duration)
  }

  const handleTransformDom = () => {
    if (carouselRef.current) {
      const translateX = carouselRef.current.clientWidth / childCount
      carouselRef.current.style.transform = `translateX(-${
        translateX * currIndex
      }px)`
    }
  }

  useEffect(() => {
    setCurrIndex(INIT_INDEX)
  }, [])

  useEffect(() => {
    handleLoadCarousel()

    return () => {
      timer && clearTimeout(timer)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  useEffect(() => {
    handleTransformDom()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currIndex])

  return (
    <div className={prefixCls}>
      <div className={`${prefixCls}__content`} ref={carouselRef}>
        {Children.map(children, (child, index) => {
          return <div className={`${prefixCls}__content__item`}>{child}</div>
        })}
      </div>
      <Dots duration={duration} currIndex={currIndex} count={childCount} />
    </div>
  )
}
