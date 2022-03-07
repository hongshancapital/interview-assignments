import React, { createContext, useContext, useEffect, useRef, useState } from 'react'
import { SHOW_TIME, TRANSITION_TIME } from './options'
import styles from './index.module.css'

interface CarouselProps {
  // 切换时间间隔
  interval?: number,
  // 切换时过度时间
  speed?: number,
  children?: React.ReactNode[]
}

interface CarouselItemProps {
  children?: React.ReactNode
}

interface LayoutInfo {
  containerWidth: number,
  itemWidth: number
}

const CarouselContext = createContext<LayoutInfo>({
  containerWidth: 0,
  itemWidth: 0
})

function CarouselItem(props: CarouselItemProps) {
  const { itemWidth } = useContext(CarouselContext)
  return (
    <div className={styles.carousel__item} style={{ width: itemWidth }}>{ props.children }</div>
  )
}

export default function Carousel(props: CarouselProps) {
  const { interval = SHOW_TIME, speed = TRANSITION_TIME, children = [] } = props
  // 最外层容器 DOM
  const carouselRef = useRef<HTMLDivElement>(null)
  // 记录一些布局信息
  const [layoutInfos, setLayoutInfos] = useState<LayoutInfo>({
    containerWidth: 0,
    itemWidth: 0
  })
  // 记录当前展示的幻灯片索引
  const [active, setActive] = useState<number>(0)

  /**
   * 初始化相关 DOM 信息
   */
  useEffect(() => {
    const { clientWidth } = carouselRef.current!
    setLayoutInfos({
      containerWidth: clientWidth * children.length,
      itemWidth: clientWidth
    })
  }, [children])

  return (
    <CarouselContext.Provider value={layoutInfos}>
      <div className={styles.carousel} ref={carouselRef} data-testid="carousel">
        <div
          className={styles.container}
          style={{
            width: layoutInfos.containerWidth,
            transform: `translateX(-${active * layoutInfos.itemWidth}px)`,
            transitionDuration: `${speed}ms`
          }}
        >
          {children}
        </div>
        <ul className={styles.indicators}>
          {
            children.map((child, idx) => (
              <li 
                className={styles.indicator} 
                key={(child as React.ReactElement).key}
                onClick={() => {
                  setActive(idx)
                }}
              >
                <span 
                  className={`${styles.indicator__bar} ${active === idx ? styles['indicator-active']: '' }`}
                  style={{ animationDuration: `${interval}ms` }}
                  onAnimationEnd={() => {
                    setActive(idx => idx >= children.length - 1 ? 0 : idx + 1)
                  }}
                />
              </li>
            ))
          }
        </ul>
      </div>
    </CarouselContext.Provider>
  )
}

Carousel.Item = CarouselItem
