import React, { useEffect, useState } from 'react'
import './index.scss'
export interface CarouselProps {
  children: React.ReactNode
  className?: string
  delay?: number
  style?: React.CSSProperties
}
interface CarouselItemProps {
  className?: string
  style: React.CSSProperties
  children: React.ReactNode
}
interface CarouselInfoProps {
  title1?: string
  title2?: string
  content1?: string
  content2?: string
  img: string
}

export const CarouselItem: React.FC<CarouselItemProps> = (
  props: CarouselItemProps
) => {
  const { className, style, children } = props
  return (
    <div
      className={
        className ? `smable-carousel-item ${className}` : `smable-carousel-item`
      }
      style={style}
    >
      {children}
    </div>
  )
}
export const CarouselInfo: React.FC<CarouselInfoProps> = (
  props: CarouselInfoProps
) => {
  const { title1, title2, content1, content2, img } = props
  return (
    <div className='smable-carousel-info'>
      <div className='smable-carousel-info-1'>
        <h1>{title1 ? title1 : ''}</h1>
        <h1>{title2 ? title2 : ''}</h1>
        <p>{content1 ? content1 : ''}</p>
        <p>{content2 ? content2 : ''}</p>
      </div>
      <div className='smable-carousel-info-2'>
        <img src={img} alt='' className='smable-carousel-image' title='img' />
      </div>
    </div>
  )
}
const Carousel: React.FC<CarouselProps> = (props: CarouselProps) => {
  const { className, children, delay, style } = props
  const time = (delay! / 1000).toFixed(0)
  //初始化索引值
  const [activeIndex, setActiveIndex] = useState(0)
  //更新索引值的方法
  const updateIndex = (newIndex: number) => {
    if (newIndex < 0) {
      newIndex = React.Children.count(children) - 1
    } else if (newIndex >= React.Children.count(children)) {
      newIndex = 0
    }
    setActiveIndex(newIndex)
    replayAnimations()
  }
  //重置dot动画
  const replayAnimations = () => {
    document.getAnimations().forEach((item) => {
      item.cancel()
      item.play()
    })
  }
  //点击dot的回调
  const clickIndex = (index: number) => {
    updateIndex(index)
    replayAnimations()
  }
  //副作用初始化定时器和取消定时器
  useEffect(() => {
    const timer = setInterval(() => {
      updateIndex(activeIndex + 1)
    }, delay)
    return () => {
      if (timer) {
        clearInterval(timer)
      }
    }
  })
  //插槽处理
  const renderChildren = () => {
    return React.Children.map(children, (child) => {
      const childElement =
        child as React.FunctionComponentElement<CarouselItemProps>
      return React.cloneElement(childElement)
    })
  }
  //视图区域
  return (
    <div
      className={className ? `smable-carousel ${className}` : `smable-carousel`}
      style={style}
    >
      <div
        className='wrapper'
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {renderChildren()}
      </div>
      <div className='dot'>
        {React.Children.map(children, (_, index) => {
          return (
            <div
              className='outter'
              onClick={() => {
                clickIndex(index)
              }}
            >
              <div
                className='inner'
                style={{
                  animationDuration: index === activeIndex ? `${time}s` : '0s',
                  backgroundColor: index !== activeIndex ? `#5e5a5a1a` : `grey`,
                }}
                title='dots'
              ></div>
            </div>
          )
        })}
      </div>
    </div>
  )
}
export default Carousel
Carousel.defaultProps = {
  delay: 3000,
}
