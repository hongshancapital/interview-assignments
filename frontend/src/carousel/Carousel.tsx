import React, { useState, useRef, useEffect, useMemo } from "react";
import './Carousel.css'

interface Config {
  num: number // 轮播页数
  duration: number // 轮播时间
  // ...other 扩展项
}

interface Props {
  children: React.ReactNode;
  config?: Partial<Config>
}

let timer: number = 0;

const Swipe: React.FC<Props> = (props) => {

  // props
  const { num = 1, duration = 1500 } = props.config || {}

  // 轮播索引
  const [curIndex, setCurIndex] = useState(-1)

  // 获取DOM
  const wrapperDOM = useRef<HTMLDivElement>(null)
  const scrollDOM = useRef<HTMLDivElement>(null)
  const slideDOM = useRef<HTMLDivElement>(null)
  const [slideItemsDOM, setSlideItemsDOM] = useState<Element[]>([])
  const [wrapperWidth, setWrapperWidth] = useState<number>(0)
  const [wrapperHeight, setWrapperHeight] = useState<number>(0)

  // 初始化scroll尺寸
  const initSwipeSize = () => {
    const _wrapperWidth: number = wrapperDOM.current?.offsetWidth || 0
    const _wrapperHeight: number = wrapperDOM.current?.offsetHeight || 0
    setWrapperWidth(_wrapperWidth)
    setWrapperHeight(_wrapperHeight)
    if (scrollDOM.current) {
      scrollDOM.current.style.width = `${num * _wrapperWidth}px`
      scrollDOM.current.style.height = `${num * _wrapperHeight}px`
    }
    if (slideDOM.current?.childElementCount) {
      setSlideItemsDOM(Array.from(slideDOM.current.children))
    }
  }

  // 初始化定时器
  const initTimer = () => {
    clearInterval(timer)
    setCurIndex(0)
    timer = window.setInterval(() => {
      setCurIndex(count => {
        return count >= num - 1 ? 0 : count + 1
      })
    }, duration)
  }

  // 初始化
  useEffect(() => {
    initSwipeSize()
    initTimer()
  }, [])

  // 轮播切换
  useEffect(() => {
    if (curIndex === -1 || !slideItemsDOM.length) return;
    slideItemsDOM.forEach(el => {
      el.children[0].setAttribute('style', 'width: 0; transition: unset;')
    })
    const curSlideItem = slideItemsDOM[curIndex]
    curSlideItem.children[0].setAttribute('style', `width: 100%; transition: width ${duration / 1000}s linear;`)
    if (scrollDOM.current) {
      scrollDOM.current.style.transition = `all ${1}s linear`
      scrollDOM.current.style.transform = `translateX(-${wrapperWidth * curIndex}px)`
    }

  }, [curIndex])

  return (
    <div ref={wrapperDOM} className="carousel">
      <div ref={scrollDOM} className="scroll">
        {props.children}
      </div>
      <div ref={slideDOM} className="slide">
        {
          new Array(num).fill(0).map((v, i) => <div key={`item_${i}`} className="slide_item">
            <div className="slide_item_progress"></div>
          </div>)
        }
      </div>
    </div>
  )
}

export default Swipe