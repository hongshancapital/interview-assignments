import React, { ReactElement, useState, useEffect, useRef } from 'react'

import InnerSlider from './InnerSlider'

import './index.css'

export interface CarouselProps {
  children: React.ReactNode
}

export default function Carouse({children}: CarouselProps): ReactElement {

  // 轮播图当前的下标索引
  const [ currentIndex, setCurrentIndex ] = useState<number>(0)

  // 执行过渡动画的DOM
  const wrapperRef = useRef<HTMLDivElement>(null)

  // 将chilren转为数组（只有一个时，不为数组）
  const newChildren = React.Children.toArray(children)

  // 在后面追加第一个轮播图内容
  newChildren.push(
    React.cloneElement(<>{ newChildren[0] }</>, {
      key: newChildren.length
    })
  )

  /**
   * 设置监听函数
   */
  // useEffect(() => {
  //   const timer = setInterval(handleRight, 2000)
  //   return () => clearInterval(timer)
  // })

  /**
   * 轮播图下标索引改变时，为过渡效果重新赋值
   */
  useEffect(() => {
    // 设置过渡动画
    if (currentIndex === 0) wrapperRef!.current!.style.transition = '0.5s ease' 
  }, [currentIndex])

  /**
   * 过渡动画结束
   */
  const handleTransitionEnd = () => {
    if (currentIndex === newChildren.length - 1) {
      // 清除过渡动画
      wrapperRef!.current!.style.transition = 'none'
      // 轮播图翻滚到第一页
      setCurrentIndex(0)
    }
  }

  // 滚动轮播图
  const handleRight = () => setCurrentIndex(currentIndex + 1)

  // 轮播图平移位置
  const wrapperStyle = {
    transform: `translateX(-${currentIndex * 100}%)`
  }
  
  return (
    <div className='carousel'>
      <div 
        ref={wrapperRef}
        className="carousel_wrapper" 
        style={wrapperStyle}
        onTransitionEnd={handleTransitionEnd}
         >
        { newChildren }
      </div>
      <InnerSlider 
        length={newChildren.length - 1}
        currentIndex={currentIndex}
        animationEnd={handleRight}
       />
    </div>
  )
}