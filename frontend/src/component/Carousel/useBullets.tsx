import { useContext, useEffect, useState } from 'react'
import { TransitionState } from '../../hook/useCSSTransition'
import { CarouselContext } from './CarouselContext'


/**
 * 为渲染轮播图的提示子弹提供底层支持
 */

export const useBullets = () => {
  const context = useContext(CarouselContext)!
  const [activeIndex, setActiveIndex] = useState(0)
  const [state, setState] = useState(TransitionState.START)

  useEffect(() => {

    const stop = context.on(
      [TransitionState.PREPARE, TransitionState.ENTER],
      (topic, { current }) => {
        setState(topic)
        setActiveIndex(current)
      }
    )
    return stop
  }, [context])

  return {active : activeIndex, state, options : context.options}
}
