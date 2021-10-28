import React, { CSSProperties } from "react"
import classes from './carousel.module.scss'
import { useCarousel } from "./useCarousel"
import { MyBullets } from "./MyBullets"

type CarouselProps = {
  wait? : number,
  duration? : number,
  children: React.ReactElement[] | React.ReactElement,
  style? : CSSProperties,
} 

/**
 * 
 * 一个简单的轮播图组件
 * 1. Transition 提供动画
 * 2. Scroller提供滚动
 * 3. useCarousel提供算法
 * 
 * @param param0 
 * @returns 
 */
export const Carousel = ({
  children,
  wait = 1500,
  duration = 500,
  style
}: CarouselProps) => {
  const list: JSX.Element[] = Array.isArray(children)
    ? children
    : [children]
  

  const {position, scrollerStyle, state} = useCarousel(list.length, duration, wait)

  return (
    <div className={classes.carousel}>
      <Scroller style={{...scrollerStyle, ...style}}>
        {list.map( (item, i) => {
          return <CarouselElement key={i}>{item}</CarouselElement>
        })}
      </Scroller>
      <div className={classes.addons}>
        <MyBullets 
          duration={duration} 
          position={position} 
          size={list.length}
          state={state} />
      </div>
    </div>
  )
}


const CarouselElement = ({
  children,
}: {
  children: JSX.Element
}) => {
  const c = children.props.className || ""
  return React.cloneElement(children, {
    className: c ? `${c} ${classes.card}` : classes.card,
  })
}



/**
 * 用它实现Carousel的滚动部分
 */
type ScrollerProps = {
  style?: CSSProperties
  children: JSX.Element | JSX.Element[]
  className?: string
}

const Scroller = ({
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