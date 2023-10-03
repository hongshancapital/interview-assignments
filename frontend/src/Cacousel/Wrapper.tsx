import './wrapper.css'
import {ReactNode, useState} from 'react'
import type {FC} from 'react'
import { useEffect, useRef, useCallback } from 'react'
import Indicator from "./Indicator"

export type props = {
  children: ReactNode
  count?: number,
  delay?: number,
  showDot?: boolean
}

const Wrapper:FC<props> = (props)=> {
  const {children, count=0, delay=200, showDot=true} = props
  const [stepNum, setStepNum] = useState<number>()
  
  const stepRef = useRef<number>(0)
  const ContainerRef = useRef<HTMLDivElement>(null)
  const timerRef = useRef<ReturnType<typeof setInterval>>()

  // 移动指令 
  const moveTo = useCallback((step: number)=> {
    ContainerRef!.current!.style!.transform = `translateX(-${(step * 100)}%)`
    setStepNum(step)
  }, [])

  // 轮播边界判断 这块可以细化 图片懒加载 轮播策略 
  const judge = useCallback(()=>{
    stepRef.current++
    if(stepRef.current > count-1)stepRef.current = 0
  }, [count])

  // 触发开关
  const triggerFn = useCallback((): ReturnType<typeof setInterval>=>{
    const timer = setInterval(()=>{
      moveTo(stepRef.current)
      judge()
    }, delay)
    return timer
  }, [delay, judge, moveTo])

  // 销毁
  const destoryTimer = useCallback((timer: ReturnType<typeof setInterval> | undefined)=>
  {
    timer&&clearInterval(timer)
  }, [])

  // 点击dot的逻辑
  const clickDot = useCallback((step: number): void =>{
    moveTo(step) 
    stepRef.current = step
  }, [moveTo])

  // 自动轮播启动
  useEffect(():()=>void=>{
    timerRef.current = triggerFn()
    // 自动销毁
    return ()=>{
      destoryTimer(timerRef.current)
    }
  }, [triggerFn, destoryTimer])

  return (
    <div className='h-100 hidden w-100 relative'>
      <div ref={ContainerRef} className='flex h-100 transition w-100'>
        {children}
      </div>
      <div className="w-100 absolute absolute-bottom-center">
        {showDot&&<Indicator count={count} clickDot={clickDot} current={stepNum} delay={delay}/>}
      </div>
    </div>
  )
}

export default Wrapper
