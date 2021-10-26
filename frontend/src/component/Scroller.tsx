import React, {CSSProperties} from 'react'
import classes from './scroller.module.scss'

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