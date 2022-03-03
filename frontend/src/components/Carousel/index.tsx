import React, { useEffect, useRef, useState } from 'react'
import { SHOW_TIME, TRANSITION_TIME, assets } from './options'
import styles from './index.module.css'

interface Options {
  // 切换时间间隔
  interval?: number
}
interface LayoutInfo {
  containerWidth: number,
  itemWidth: number
}

export default function Carousel(options: Options) {
  const carouselRef = useRef<HTMLDivElement>(null)
  // 记录每一张幻灯片开始展示时刻
  const startTime = useRef<number>(0)
  // 记录在标签页切换时，当前标签页已展示的时间
  const t0 = useRef<number>(0)

  const [layoutInfos, setLayoutInfos] = useState<LayoutInfo>({
    containerWidth: 0,
    itemWidth: 0
  })

  // 记录当前展示的幻灯片索引
  const [active, setActive] = useState<number>(0)
  // 记录当前幻灯片已展示的进度，0 - 1
  const [progress, setProgress] = useState<number>(0)

  /**
   * 初始化相关 DOM 信息
   */
  useEffect(() => {
    const { clientWidth } = carouselRef.current!
    setLayoutInfos({
      containerWidth: clientWidth * assets.length,
      itemWidth: clientWidth
    })
  }, [])

  /**
   * 处理当前浏览器标签页隐藏-显示时动画衔接
   */
  useEffect(() => {
    function visibilityChangeHandle() {
      if (window.document.hidden) {
        t0.current = Date.now() - startTime.current
        return
      }
      startTime.current = Date.now() - t0.current
      t0.current = 0
    }

    window.document.addEventListener('visibilitychange', visibilityChangeHandle)
    return () => {
      window.document.removeEventListener('visibilitychange', visibilityChangeHandle)
    }
  }, [])

  /**
   * 启动轮播
   */
  useEffect(() => {
    let mounted = true
    startTime.current = Date.now()

    function startCarousel() {
      const { interval = SHOW_TIME } = options
      const now = Date.now()
      const displayedTime = now - startTime.current

      if (displayedTime >= interval) {
        setProgress(0)
        startTime.current = Date.now()
        setActive(idx => idx >= assets.length - 1 ? 0 : idx + 1)
      } else {
        setProgress(displayedTime / interval)
      }

      if (mounted) {
        requestAnimationFrame(startCarousel)
      }
    }
    
    startCarousel()

    return () => {
      mounted = false
    }
  }, [])

  return (
    <div className={styles.carousel} ref={carouselRef} data-testid="carousel">
      <div 
        className={styles.container} 
        style={{
          width: layoutInfos.containerWidth,
          transform: `translateX(-${active * layoutInfos.itemWidth}px)`,
          transitionDuration: `${TRANSITION_TIME}ms`
        }}
      >
        {
          assets.map(asset => (
            <div 
              className={styles.carousel__item} 
              key={asset.id} 
              style={{ width: layoutInfos.itemWidth, backgroundColor: asset.backgroundColor }}
            >
              { asset.children }
            </div>
          ))
        }
      </div>
      <ul className={styles.indicators}>
        {
          assets.map((i, idx) => (
            <li className={styles.indicator} key={i.id}>
              {
                active === idx
                  ? (<span className={styles.indicator__bar} style={{ width: `${progress / 1 * 100}%` }} />)
                  : null
              }
            </li>
          ))
        }
      </ul>
    </div>
  )
}