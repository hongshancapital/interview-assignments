import React, { MouseEventHandler, useEffect, useRef, useState } from 'react'
import './Carousel.css'

type CarouselPropContent = {
  title?: string // content.title 内容标题
  subtitle?: string // content.subtitle 内容子标题
  color?: string // content.color 内容文字颜色, support rgba or hex
  background: string //  content.background 内容背景图, (required) only support image url or base64
  backgroundColor?: string // content.backgroundColor 内容背景色, support rgba or hex
}

type CarouselItemProps = {
  content?: CarouselPropContent // Prop content 轮播图单项内容
  onClick?: MouseEventHandler<HTMLDivElement> // Event onClick 单项点击事件
}

type CarouselProps = {
  className?: string // Prop className 样式配置
  style?: React.CSSProperties // Prop style 样式配置
  autoplay?: boolean // Prop autoplay 是否自动播放
  duration?: number // Prop duration 自动播放间隔
  defaultActiveIndex?: number // Prop defaultActiveIndex 默认选中项目
  onChange?: (currentIndex: number) => void // Event onChange 状态改变时回调
}

export const CarouselItem: React.FC<CarouselItemProps> = props => {
  const { content, children, onClick } = props

  // render 有 content 时, 优先渲染 content
  if (content) {
    return (
      <div
        className="carousel-image-item"
        style={{
          color: content.color ? content.color : '#000000',
          backgroundImage: `url(${content.background})`,
          backgroundColor: content.backgroundColor ? content.backgroundColor : 'transparent'
        }}
        onClick={onClick}>
        <div className="carousel-image-content">
          {content.title && <div className="carousel-image-title">{content.title}</div>}
          {content.subtitle && <div className="carousel-image-subtitle">{content.subtitle}</div>}
        </div>
      </div>
    )
  }

  // render 无 content 时，渲染子节点
  return <div className="carousel-image-item">{children}</div>
}

export const Carousel: React.FC<CarouselProps> = props => {
  const { className = '', style, autoplay, duration = 3000, defaultActiveIndex, children, onChange } = props
  const count: number = children instanceof Array ? children.length : children ? 1 : 0 // 轮播图数量
  const list = Array(count).fill(null) // 构造轮播图
  const [animationIndex, setAnimationIndex] = useState(defaultActiveIndex || 0) // 当前动画索引
  const timer = useRef(-1) // 动画计时器

  // effect 监听动画更新
  useEffect(() => {
    clearTimeout(timer.current) // 销毁上次 timeout
    if (autoplay) {
      // 设置下一次自动播放 timeout
      timer.current = Number(
        setTimeout(() => {
          // 更新当前动画索引
          const currentIndex = animationIndex >= count - 1 ? 0 : animationIndex + 1
          setAnimationIndex(currentIndex)
          onChange && onChange(currentIndex)
        }, duration)
      )
    }
    return () => {
      // 销毁计时器
      clearTimeout(timer.current)
    }
  }, [autoplay, animationIndex])

  // render 渲染组件
  return (
    <div className={`carousel ${className}`} style={style}>
      <div className="carousel-container">
        <div
          className="carousel-image-list"
          style={{
            width: `${count * 100}%`,
            transform: `translate3d(-${(animationIndex * 100) / count}%, 0, 0)`
          }}>
          {children}
        </div>
        <div className="carousel-pagination">
          {count > 1 &&
            list.map((item, index) => {
              return (
                <div
                  key={`pagination_${index}`}
                  className="carousel-pagination-dot"
                  onClick={() => {
                    setAnimationIndex(index)
                  }}>
                  <div
                    className="carousel-pagination-dot-inner"
                    style={{
                      animation:
                        autoplay && index === animationIndex
                          ? `carousel-pagination-dot-animation ${duration}ms`
                          : 'none'
                    }}></div>
                </div>
              )
            })}
        </div>
      </div>
    </div>
  )
}
