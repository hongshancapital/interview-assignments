import React from 'react'
import styles from './index.module.css'

export const CarouselIndicator: React.FC<{
  count: number;
  activeIndex: number;
  timeDuration: number;
  onClick: (arg: number) => void;
}> = ({ count, activeIndex, timeDuration, onClick }) => {
  return (
    <div className={styles.carousel_indicator_wrapper} data-testid="carousel-indicator">
      {new Array(count).fill(undefined).map((_,index) => {
        const progressClass = [
          styles.carousel_indicator_progress,
          activeIndex === index
            ? styles.carousel_indicator_progress_active
            : '',
        ].join(' ')
        const progressStyle = activeIndex === index
          ? { transitionDuration: `${timeDuration}ms`}
          : undefined
        const clickHandle = () => {
          activeIndex !== index && onClick(index)
          console.log(index, activeIndex)
        }
        return <div className={styles.carousel_indicator_item} key={index} onClick={clickHandle}>
          <span className={progressClass} style={progressStyle} />
        </div>
      })}
    </div>
  )
}
