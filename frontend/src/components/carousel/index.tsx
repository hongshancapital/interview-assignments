import React, { ReactElement, useEffect, useState } from 'react'
import CarouselDots from './dots'
import { CarouseItemType } from './item'
import './styles/index.css'

export const CarouseContext = React.createContext({
  showIndex: 0,
  delay: 2500,
  duration: 1000,
  animateType: 'linear'
})
const Carousel: React.FC<{
  className?: string;
  /**
   * 组件高度，默认100%
   */
  height?: number;
  /**
   * 轮播间隔时间，单位：毫秒
   * 默认：2500ms。建议不要小于duration值（默认1000ms）
   */
  delay?: number;
  /**
   * 单次轮播切换动画时长，单位：毫秒
   * 默认：1000ms
   */
  duration?: number;
  /**
   * 动画类型，默认linear
   */
  animateType?: 'linear' | 'easy' | 'easy-in' | 'easy-out' | 'easy-in-out' | 'step-start' | 'step-end' | string;
  children?: ReactElement<CarouseItemType>[];
}> = (props) => {
  const [showIndex, setShowIndex] = useState(-1)
  const [, setTimer] = useState<NodeJS.Timeout | null>(null)
  const {delay} = props
  const defaultDelay = 2500
  
  useEffect(() => {
    const count = props.children?.length
    setTimer(preTimer => {
      if (preTimer) {
        clearInterval(preTimer)
      } else {
        setShowIndex(0)
      }
      return setInterval(() => {
        setShowIndex(preIndex => {
          const resIndex = preIndex === (count! - 1) ? 0 : preIndex + 1
          return resIndex
        })
      }, delay || defaultDelay)
    })
    return () => {
      setTimer(preTimer => {
        if (preTimer) clearInterval(preTimer)
        return null
      })
    }
  }, [props.children, delay])

  return <div className={`carousel ${props.className || ''}`} style={{
    height: props.height || '100%'
  }}>
    <CarouseContext.Provider value={{
      showIndex,
      delay: delay || defaultDelay,
      duration: props.duration || 1000,
      animateType: props.animateType || 'linear'
    }}>
      {props.children}
    </CarouseContext.Provider>
    <CarouselDots showIndex={showIndex} count={props.children?.length || 0} delay={delay || defaultDelay}/>
  </div>
}

export default Carousel