import React, { CSSProperties, ReactElement, useLayoutEffect, useMemo, useRef, useState } from 'react'
import styles from './index.module.css'

export const CarouselItemRenderer: React.FC<{
  children?: ReactElement | ReactElement[];
  index: number;
  moveSpeed: number;
}> = ({
  children,
  index,
  moveSpeed,
}) => {
  const [width, setWidth] = useState<number>()
  const carouselWrapperRef = useRef<HTMLDivElement>(null)

  const childrenNodes = useMemo(() => {
    if (width === undefined || !children) return []
    const carouselItemStyle = { width } as CSSProperties
    if (children instanceof Array) {
      return children.map((item, index) => (
        <div
          className={styles.carousel_item}
          style={carouselItemStyle}
          key={index}
        >
          {item}
        </div>
      ))
    }
    return (
      <div
        className={styles.carousel_item}
        style={carouselItemStyle}
      >
        {children}
      </div>
    )
  }, [children, width])

  const carouselTrackStyle = useMemo(() => {
    if (width === undefined) return
    return {
      transform: `translate3d(-${index * width}px, 0, 0)`,
      transitionDuration: `${moveSpeed}ms`,
    }
  }, [index])

  useLayoutEffect(() => {
    setWidth(carouselWrapperRef.current?.offsetWidth ?? 0)
  })

  return (
    <div className={styles.carousel_track_wrapper} ref={carouselWrapperRef} data-testid="carousel-renderer">
      <div className={styles.carousel_track} style={carouselTrackStyle}>
        {childrenNodes}
      </div>
    </div>
  )
}
