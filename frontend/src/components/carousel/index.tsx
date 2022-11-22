import React, { ReactNode, useCallback, useEffect, useMemo, useRef, useState } from 'react'

import "./index.css";

interface CarouselProps {
  /**每一张播放速度*/
  playSpeed?:number;
  /**每一张切换速度*/
  slideSpeed?:number;
  children: React.ReactNode;
}

const Carousel: React.FC<CarouselProps> = ({
  playSpeed = 1000 * 4, 
  slideSpeed = 1000,
  children,
}) => {
  const [slideIndex, setSlideIndex] = useState(0)
  const [sliderW, setSliderW] = useState(0)
  const carouselRef = useRef<HTMLDivElement>(null)

  const innerSlideSpeed:number =  useMemo(()=>{
    return slideSpeed > playSpeed ? playSpeed : slideSpeed
  },[playSpeed, slideSpeed])

  const innerChildren:ReactNode[] = useMemo(() => {
    return React.Children.map(children, (item,index) => (
      <div key={index} className='slider-item' style={{ width: sliderW }}>
        {item}
      </div>
    ))||[]
  }, [children, sliderW])

  const slideTo:(slideIndex: number)=>void = useCallback(
    (slideIndex:number) => {
      setSlideIndex(slideIndex >= innerChildren.length ? 0 : slideIndex)
    },
    [innerChildren.length]
  )

  const handleAnimationEnd=useCallback((endSlideIndex:number)=>{
    slideTo(endSlideIndex+1)
  },[slideTo])

  // 底部进度条样式
  const progressStyle = {
    animationName: 'width-animation',
    animationDuration: `${playSpeed / 1000}s`,
    backgroundColor: '#fff'
  }

  const progressList = useMemo(() => {
    return Array.from({ length: innerChildren.length }, (k, i) => i)
  }, [innerChildren.length])

  // 渲染底部进度条
  const progressDom = progressList.map((item) => (
    <div key={item} className='footer-item'>
      <span
        className='progress'
        style={item === slideIndex ? progressStyle : {}}
        onAnimationEnd={() => {
          handleAnimationEnd(item)
        }}
      ></span>
    </div>
  ))

  useEffect(() => {
    if(carouselRef?.current){
      setSliderW(carouselRef.current.clientWidth) // 设置跑马灯的可视区域宽度
    }
  }, [carouselRef])

  return (
    <div className='carousel' ref={carouselRef}>
      <div
        className='slider'
        style={{
          width: sliderW * innerChildren.length,
          transform: `translateX(-${sliderW * slideIndex}px)`,
          transitionDuration: `${innerSlideSpeed / 1000}s`
        }}
      >
        {sliderW && innerChildren}
      </div>
      <div className='footer'>
        {progressDom}
      </div>
    </div>
  )
};

export default Carousel;