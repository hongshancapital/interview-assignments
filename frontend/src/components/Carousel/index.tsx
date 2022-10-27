import "./css/index.scss"

import { ICarouselProps } from "./interfaces"
import Progress from "./Progress"
import React from "react"
import useCarousel from "./hook"

const DEFAULT_SHOW_MS = 2.5
const DEFAULT_DURATION = 0.5
// 配合scss动态class，需要限制子元素长度
const MAX_SIZE = 10
/**
 * 跑马灯组件主文件
 */
const Carousel: React.FC<ICarouselProps> = ({
  children,
  showTime = DEFAULT_SHOW_MS,
  duration = DEFAULT_DURATION,
}) => {
  const size = children?.length || 0
  
  if(size > MAX_SIZE) {
    throw new Error(`子元素个数不能超过${MAX_SIZE}`)
  }

  const { activeIndex, setActiveIndex } = useCarousel(size, showTime)
  const className = "page-wrapper translate-x-" + activeIndex
  return (
    <div className="carousel-slider">
      <div
        className={className}
        style={{ transition: `transform ${duration}s` }}
      >
        {children}
      </div>

      <Progress
        size={size}
        handleClick={setActiveIndex}
        activeIndex={activeIndex}
        showTime={showTime}
      ></Progress>
    </div>
  )
}

export default Carousel

