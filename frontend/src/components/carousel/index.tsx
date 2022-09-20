import React, { useEffect, useState } from 'react'
import CarouselDots from './dots'
import './styles/index.css'

export const CarouseContext = React.createContext({
  showIndex: 0,
  delay: 2500,
  duration: 1000,
  animateType: 'linear'
})
function isArray (obj: any): boolean {
  return Object.prototype.toString.call(obj) === '[object Array]'
}
function getChildrenCount (children: boolean | React.ReactChild | React.ReactFragment | React.ReactPortal | null | undefined): number {
  if (!isArray(children)) return 0
  let count = 0;
  (children as []).forEach(item => {
    if (isArray(item)) {
      count += (item as []).length
    } else {
      count += 1
    }
  })
  return count
}
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
}> = (props) => {
  const [showIndex, setShowIndex] = useState(-1)
  const [, setTimer] = useState<NodeJS.Timeout | null>(null)
  const {delay} = props
  const defaultDelay = 2500
  
  useEffect(() => {
    const count = getChildrenCount(props.children)
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
    height: props.height || undefined
  }}>
    <CarouseContext.Provider value={{
      showIndex,
      delay: delay || defaultDelay,
      duration: props.duration || 1000,
      animateType: props.animateType || 'linear'
    }}>
      {props.children}
    </CarouseContext.Provider>
    <CarouselDots showIndex={showIndex} count={getChildrenCount(props.children)} delay={delay || defaultDelay}/>
  </div>
}

export default Carousel