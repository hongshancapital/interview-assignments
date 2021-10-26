import { CSSProperties } from 'react'
import {useFlow} from './useFlow'


/**
 * 通用组件，处理CSS动画播放
 * 基于状态机，不对异步时序做任何假设
 * 播放一次CSS动画
 */
export type CSSTransitionOptions = {
  initialStyle: CSSProperties, // 初始样式
  wait: number, // 动画播放等待
  duration: number, // 动画播放时间
  loop?: boolean,
  transitions: {
    prepare: CSSProperties,
    enter: CSSProperties,
    leave: CSSProperties
  },
  enabled?: boolean, // 工作标识
  on?: (
    topic: TransitionState,
    context?: { cancel: () => void }
  ) => void
}


export enum TransitionState {
  START,
  PREPARE,
  ENTER,
  LEAVE,
  FINISH
}



/**
 * 时序关系如下
 * | start ---- prepare ---- enter ---- leave |
 *                 |   wait    |    play      |
 * @param param0 
 * @returns 
 */
export function useCSSTransition({
  wait,
  duration,
  initialStyle,
  transitions,
  loop = false,
  enabled = true,
  on
}: CSSTransitionOptions) {

  const style = useFlow({
    initialState: TransitionState.START,
    initialValue: initialStyle,
    enabled,
    flows: [
      {
        to: TransitionState.PREPARE,
        value: transitions.prepare,
        delay : 'nextTick',
      },
      {
        to: TransitionState.ENTER,
        value: transitions.enter,
        delay: wait,
      },
      {
        to : TransitionState.LEAVE,
        value : transitions.leave,
        delay : duration
      },
      {
        to : TransitionState.FINISH,
        value : transitions.leave,
        delay : "immediate"
      }
    ],
    loop,
    on : (state, context) => {
      on && on(state, context)
    }
  })

  return style 
}
