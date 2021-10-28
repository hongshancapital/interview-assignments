import { CSSProperties, useState, useEffect, useRef } from 'react'


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
  on,
}: CSSTransitionOptions): [TransitionState, CSSProperties] {
  const [state, style] = useFlow({
    initialState: TransitionState.START,
    initialValue: initialStyle,
    enabled,
    flows: [
      {
        to: TransitionState.PREPARE,
        value: transitions.prepare,
        delay: "nextTick",
      },
      {
        to: TransitionState.ENTER,
        value: transitions.enter,
        delay: wait,
      },
      {
        to: TransitionState.LEAVE,
        value: transitions.leave,
        delay: duration,
      },
      {
        to: TransitionState.FINISH,
        value: transitions.leave,
        delay: "immediate",
      },
    ],
    loop,
    on: (state, context) => {
      on && on(state, context);
    },
  });

  return [state, style];
}


/**
 * 流程转换控制
 * 例如：可以用于播放CSS动画
 */

type FlowConfig<S, T> =  {
  to : S,
  value : T,
  delay : number | 'immediate' | 'nextTick'
} 


type FlowOptions <S, T> = {
  initialState : S,
  initialValue : T,
  enabled : boolean,
  flows : FlowConfig<S, T>[],
  loop? : boolean,
  on? : (state : S, context? : {cancel : () => void}) => void 
}


export function skeduler(callback : () => void, delay : number | 'immediate' | 'nextTick') {
  if(delay === 'immediate') {
    callback()
    return
  }
  if(delay === 'nextTick') {
    requestAnimationFrame(() => {
      callback()
    })
    return
  }
  setTimeout(() => {
    callback()
  }, delay)
}


enum FlowState {
  Started,
  Stoped
}
export function useFlow<S, T>({
  initialState,
  initialValue,
  flows,
  on,
  enabled = true,
  loop = false,
}: FlowOptions<S, T>) : [S, T] {
  const [_state, setState] = useState<[number, S, T]>([
    -1,
    initialState,
    initialValue,
  ])
  const [index, state, value] = _state

  const flowState = useRef(FlowState.Started)

  function cancel(){
    flowState.current = FlowState.Stoped
  }

  function play(i: number) {
    if (flowState.current === FlowState.Stoped) {
      return
    }
    if (i === flows.length) {
      if(loop) {
        setState([0, initialState, initialValue])
      }
      return
    }
    const { to, delay, value } = flows[i]
    skeduler(() => {
      setState([i + 1, to, value])
    }, delay)
  }

  useEffect(() => {
    if(enabled) {
      flowState.current = FlowState.Started
      setState([0, initialState, initialValue])
    }
  }, [enabled])

  useEffect(() => {
    if(index === -1) {
      return
    }
    on && on(state, {
      cancel
    })
    play(index)
  }, [index])

  return [state, value]
}