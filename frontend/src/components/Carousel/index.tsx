import React, { useEffect, useRef, useState } from 'react'
import { DEFAULT_PLAY_DURATION } from './constants'
import styles from './index.module.scss'
import cx from 'classnames'
import useAutoPlay from './useAutoPlay'
import CarouselDot from './CarouselDot'
import { useMemoizedFn, useMount } from 'ahooks'
import _ from 'lodash'

export interface CarouselProps {
  /** Pause play on mouse hover */
  pauseDuringFocus?: boolean
  /** Whether to play automatically */
  autoPlay?: boolean
  /** Playback interval */
  duration?: number
  items?: React.ReactNode[]
  style?: React.CSSProperties
  className?: string
}

const Carousel: React.FC<CarouselProps> = (props) => {
  const [containerWidth, setContainerWidth] = useState(0)
  const containerRef = useRef<HTMLDivElement>(null)
  const startedPlay = useRef(false)
  const {
    autoPlay = true,
    items,
    duration = DEFAULT_PLAY_DURATION,
    pauseDuringFocus = true,
    style,
    className,
  } = props

  const itemLength = items?.length ?? 0

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
    dotLength: itemLength,
  })

  const carouselItemList = items?.length ? [...items, items[0]] : []
  const transform = `translate(-${
    (startedPlay.current && currentFrameNumber === 0
      ? itemLength
      : currentFrameNumber) * containerWidth
  }px, 0px)`

  const transitionEndHandler: React.TransitionEventHandler<HTMLDivElement> =
    useMemoizedFn((event) => {
      startedPlay.current = true
      const isFirstOne = currentFrameNumber === 0
      if (isFirstOne) {
        const fakeLastOneTransform = `translate(0px, 0px)`;
        const transformContainer = event.target as HTMLDivElement;
        transformContainer.style.setProperty('transition-property', 'none');
        transformContainer.style.setProperty('transform', fakeLastOneTransform);
        setTimeout(() => {
          transformContainer.style.setProperty('transition-property', 'all');
        }, 0);
      }
    })

  useEffect(() => {
    if (containerRef.current) {
      const observer = new ResizeObserver(function (entries) {
        const target = entries?.[0].target as HTMLDivElement | null
        if (target && _.isNumber(target.offsetWidth)) {
          setContainerWidth(target.offsetWidth)
        }
      })
      observer.observe(containerRef.current)
      return () => observer.disconnect()
    }
  }, [])

  return (
    <div
      className={cx(styles.carousel, className)}
      style={style}
      ref={containerRef}
      onMouseEnter={containerFocus}
      onMouseLeave={containerFocusLeave}
    >
      <div
        className={cx(styles.carouselContainer)}
        style={{
          transform,
        }}
        onTransitionEnd={transitionEndHandler}
      >
        {carouselItemList?.map((carouselItem, index) => (
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
      </div>
      <CarouselDot
        dotNumber={items?.length}
        onDotClick={onSelectFrame}
        currentFrameNumber={currentFrameNumber}
      />
    </div>
  )
}

export default Carousel
