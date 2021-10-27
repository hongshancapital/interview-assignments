import React, {CSSProperties} from 'react'
import classes from './scroller.module.scss'

/**
 * 用它实现Carousel但是和Carousel无关，可以复用
 */
type ScrollerProps = {
  style?: CSSProperties
  children: JSX.Element | JSX.Element[]
  className?: string
}
export const Scroller = ({
  children,
  style,
  className,
}: ScrollerProps) => {
  return (
    <div className={`${classes["scroll-view"]} ${className}`}>
      <div
        className={classes["scroller"]}
        style={style}
      >
        {children}
      </div>
    </div>
  )
}