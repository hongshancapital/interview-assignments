import React, { CSSProperties, useRef } from "react"
import { Scroller } from '../Scroller'
import { useAlg } from "./useAlg"
import {AlgMode, AlgOptions} from './AbstractAlg'
import {CarouselViewport} from './CarouselViewport'
import classes from './carousel.module.scss'
import {CarouselContext, CarouselContextInst} from './CarouselContext'
import { TransitionState, useCSSTransition } from "../../hook/useCSSTransition"

type CarouselProps = {
  wait? : number,
  duration? : number,
  addons ? : React.ReactElement[] | React.ReactElement,
  children: React.ReactElement[] | React.ReactElement,
  style? : CSSProperties,
} & Omit<AlgOptions, "current" | "size">


/**
 * 将Transition/Viewport/滑动算法/Context:4部分组成轮播图
 * Transition 提供动画
 * 滑动算法是动画的编排
 * Viewport支持虚拟化
 * Context为基于轮播图扩展组件提供支持（例如实现Bullets)
 * @param param0 
 * @returns 
 */
export const Carousel = ({
  children,
  wait = 1500,
  duration = 500,
  style,
  addons,
  mode = "loop",
  dir = "left"
}: CarouselProps & {
  mode : AlgMode
}) => {
  const list: JSX.Element[] = Array.isArray(children)
    ? children
    : [children]
  
  const { viewport, next, styles, playIndex } = useAlg({
    current : 0,
    size : list.length,
    dir,
    duration,
    mode,
  })

  const context = useRef(new CarouselContextInst({
    size: list.length,
    wait,
    duration,
    dir
  }))

  const scrollerStyle = useCSSTransition({
    transitions : styles,
    duration,
    wait,
    initialStyle : styles.prepare,
    loop : true,
    on(topic) {
      context.current.emit(topic, { current : playIndex() })
      if(topic === TransitionState.FINISH) {
        next()
      }
    }
  })

  return (
    <CarouselContext.Provider value={context.current}>
      <div className={classes.carousel}>
        <Scroller style={{...scrollerStyle, ...style}}>
          <CarouselViewport viewport={viewport}>
            {list}
          </CarouselViewport>
        </Scroller>
        <div className={classes.addons}>{addons}</div>
      </div>
    </CarouselContext.Provider>
  )
}

