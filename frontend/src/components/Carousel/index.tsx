import React, { useRef } from 'react'
import { DEFAULT_PLAY_DURATION } from './constants'
import styles from './index.module.scss'
import cx from 'classnames'
import useAutoPlay from './useAutoPlay'
import CarouselDot from './CarouselDot'

export interface CarouselProps extends React.HTMLAttributes<HTMLDivElement> {
  /** Pause play on mouse hover */
  pauseDuringFocus?: boolean
  /** Whether to play automatically */
  autoPlay?: boolean
  /** Playback interval */
  duration?: number
  items?: React.ReactNode[]
}

const Carousel: React.FC<CarouselProps> = (props) => {
  const containerRef = useRef<HTMLDivElement>(null)
  const {
    autoPlay = true,
    items,
    duration = DEFAULT_PLAY_DURATION,
    pauseDuringFocus = true,
    style,
    className,
  } = props

  const {
    containerFocus,
    containerFocusLeave,
    currentFrameNumber,
    onSelectFrame,
  } = useAutoPlay({
    container: containerRef,
    autoPlay,
    duration,
    pauseDuringFocus,
    dotLength: items?.length ?? 0,
  })

  return (
    <div
      ref={containerRef}
      className={cx(styles.carouselContainer, className)}
      style={{
        ...style,
      }}
      onMouseEnter={containerFocus}
      onMouseLeave={containerFocusLeave}
    >
      {items?.map((carouselItem, index) => (
        <div
          key={index}
          className={cx(
            styles.carouselItem,
            index === currentFrameNumber ? styles.active : ''
          )}
        >
          {carouselItem}
        </div>
      ))}
      <CarouselDot
        dotNumber={items?.length}
        onDotClick={onSelectFrame}
        currentFrameNumber={currentFrameNumber}
      />
    </div>
  )
}

export default Carousel
