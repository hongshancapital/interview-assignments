import React, { FC } from 'react'
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
  children: React.ReactNode[]
  /**轮播间隔时间，ms */
  duration?: number
}> = ({ children, duration = 2000 }) => {
  const { currIndex } = useIndex({ duration, children })

  const bodyWidth = document.body.clientWidth
  const wrapperWidth = bodyWidth * children.length

  return (
    <div className={styles.Carousel}>
      <div
        className={styles.content}
        style={{
          width: wrapperWidth,
          transform: `translateX(-${currIndex * bodyWidth}px)`,
        }}
      >
        {children}
      </div>
      <Dots num={children.length} index={currIndex} duration={duration} />
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
