import React, { FC, ReactNode } from 'react'
// import useCountdown from './hooks/useCountdown'
import useIndex from './hooks/useIndex'
import styles from './index.module.scss'

/**
 * @author blingblingredstar
 * @email 56447023@qq.com
 * @description
 * 轮播图组件
 * 当前传入children即可，目前仅支持全屏轮播
 */
const Carousel: FC<{
  /**轮播间隔时间，ms */
  duration?: number
}> = ({ children, duration = 2000 }) => {
  const childrenArray = Array.isArray(children) ? [...children] : [children]
  const { currIndex } = useIndex({ duration, children: childrenArray })

  const bodyWidth = document.body.clientWidth
  const wrapperWidth = bodyWidth * childrenArray.length

  return (
    <div className={styles.Carousel}>
      <div
        className={styles.content}
        style={{
          width: wrapperWidth,
          transform: `translateX(-${currIndex * bodyWidth}px)`,
        }}
      >
        {childrenArray}
      </div>
      <Dots num={childrenArray.length} index={currIndex} duration={duration} />
    </div>
  )
}

const Dots: FC<{
  num: number
  index: number
  duration: number
}> = ({ num, index, duration }) => {
  const dots: null[] = new Array(num).fill(null)

  return (
    <div className={styles.dots}>
      {dots.map((_, i) => (
        <span key={i} className={styles.dot}>
          <span
            className={styles.dotProgress}
            style={{
              opacity: i === index ? 1 : 0,
              animation:
                i === index
                  ? `${styles.progress} ${duration}ms linear`
                  : 'none',
            }}
          />
        </span>
      ))}
    </div>
  )
}

export default Carousel
