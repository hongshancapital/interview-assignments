import React from 'react'
import classes from './bullets.module.scss'
import { TransitionState, useCSSTransition } from '../../hook/useCSSTransition'

/**
 * 这里架构上将bullet看作轮播图之外的【非标】组件实现
 * 轮播图提供实现bullet的基础能力
 * 动画和样式全部外部实现
 * @returns 
 */
 export const MyBullets = ({position, state, size, duration} : {
   position : number,
   size : number,
   state : TransitionState,
   duration : number
 }) => {
   return (
     <div className={classes.bullets}>
       {[...Array(size)].map((_, i) => {
         return (
           <BulletRender
             key={i}
             i={i}
             active={position}
             state={state}
             wait={duration}
           />
         )
       })}
     </div>
   )
 }

const BulletRender = ({i, wait, active, state} : {
  i : number,
  active : number,
  wait : number,
  state : TransitionState
}) => {

  const time = `${(wait/1000).toFixed(2)}s`
  const transitions = {
    prepare: {
      transform: `translateX(-100%)`,
    },
    enter: {
      transform: `translateX(0%)`,
      transition: `transform ${time} linear`,
    },
    leave: {
      transform: `translateX(-100%)`,
    },
  }

  let [, style] = useCSSTransition({
    enabled : (active === i) && (state !== TransitionState.START),
    initialStyle: transitions.prepare,
    loop: false,
    wait: 0,
    duration: wait,
    transitions
  })

  return <div className={classes.bullet} >
    <div style={style} className={classes.transition}></div>
  </div>

}

