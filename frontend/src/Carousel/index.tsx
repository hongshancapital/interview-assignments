import React, { CSSProperties, ReactElement, useEffect, useMemo, useLayoutEffect } from 'react'
import { useIndicator } from './hook'
import styles from './index.module.css'
import { CarouselIndicator } from './CarouselIndicator'
import { CarouselItemRenderer } from './CarouselItemRenderer'

const defaultStyle = {
  height: '400px',
}
type CarouselProps = {
  children?: ReactElement | ReactElement[];
  style?: CSSProperties; /* 轮播样式 */
  autoPlay?: boolean; /* 自动轮播 */
  onChange?: (index: number) => void; /* 切换时事件监听 */
  timeDuration?: number; /* 单张停留时间 */
  moveSpeed?: number; /* 切换动画时长 */
  currentIndex?: number; /* 当前展示索引 */
}
export const Carousel: React.FC<CarouselProps> = ({
  children,
  style,
  autoPlay = true,
  onChange,
  timeDuration = 3000,
  moveSpeed = 300,
  currentIndex,
}) => {
  const [index, setIndex, setCount] = useIndicator({
    timeDuration,
    autoPlay,
  })
  const wrapperStyle = useMemo(() => ({ defaultStyle, ...style ?? {} }), [style])
  const carouselItemCount = useMemo(() => {
    if (!children) return 0
    return children instanceof Array ? children.length : 1
  }, [children])
  useEffect(() => {
    setCount(carouselItemCount)
  }, [carouselItemCount])
  useEffect(() => {
    onChange && onChange(index)
  }, [index])
  useLayoutEffect(() => {
    if (currentIndex !== undefined) {
      setIndex(currentIndex)
    }
  }, [currentIndex])
  
  return (
    <div className={styles.carousel} style={wrapperStyle} data-testid="carousel">
      <CarouselItemRenderer index={index} moveSpeed={moveSpeed}>
        {children}
      </CarouselItemRenderer>
      <CarouselIndicator
        activeIndex={index}
        count={carouselItemCount}
        timeDuration={timeDuration}
        onClick={setIndex}
      />
    </div>
  )
}