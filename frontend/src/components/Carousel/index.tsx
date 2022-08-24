import React, { useEffect, useState, useMemo, useRef, forwardRef, useImperativeHandle } from 'react'
// import less from './index.less'
import { staged } from 'staged-components'
import { CarouselItem } from '../CarouselItem'
import "./index.css";

const MAX_COUNT = 5
// 类名统一前缀
const classPrefix = `my-carousel`

// TODO 有时候我们只想暴露组件的部分接口给外部使用
// export type CarouselRef = {
//   carouselNext: () => void
// }

export type CarouselProps = {
  // allowTouchMove: true, // TODO 后续可以支持手动控制
  autoplay?: boolean,
  autoplayInterval?: number,
  loop?: boolean,
}

/**
 * 这里我们利用 forwardRef 来为我们的自定义组件支持 ref 调用
 * 还利用 staged 支持 "break" the rule "only call hooks at the top level"
 */

// export const Carousel = forwardRef<CarouselRef, CarouselProps>(
export const Carousel = staged<CarouselProps>((props) => {
  const {
    autoplay = true,
    autoplayInterval = 3000,
    loop = true
  } = props

  /**
   * 1. 验证子元素是否合法
   * 2. 统计子元素个数
   */
  const { validChildren, count } = useMemo(() => {
    let count = 0
    const validChildren = React.Children.map(props.children, child => {
      if (!React.isValidElement(child)) {
        return null
      }
      if (child.type !== CarouselItem) {
        console.error('The children of Carousel must be Carousel.Item components');
        return null
      }
      count++
      if (count > MAX_COUNT) {
        console.error('The number of Carousel.Item must less than 5');
        return null
      }
      return child
    })
    return {
      validChildren,
      count
    }
  }, [props.children])

  if (count === 0 || !validChildren) {
    console.error('Carousel needs at least one child');
    return null
  }

  let isLoop = loop
  if ((count - 1) < 1) {
    // 如果只有一个item
    isLoop = false
  }

  const [current, setCurrent] = useState<number>(0)
  const currentRef = useRef<any>(0)

  const carouselNext = () => {
    const index = currentRef.current
    currentRef.current = (index + 1) === (count) ? 0 : index + 1
    setCurrent(currentRef.current)
  }

  // TODO
  // useImperativeHandle(ref, () => ({
  //   carouselTo,
  //   carouselNext,
  // }))

  useEffect(() => {
    if (!autoplay) {
      return
    }
    const interval = window.setInterval(() => {
      carouselNext()
    }, autoplayInterval)
    return () => {
      window.clearInterval(interval)
    }
  }, [autoplay, autoplayInterval, count])

  const renderInner = () => {
    return (
      <div
        className="inner"
        style={
          {
            // width: `${count * 100}%`,
            transform: 'translateX(-' + current * 100 + '%)'
          }
        }
      >
        {/* render carousel items */}
        {
          React.Children.map(validChildren, (child) => {
            return child
          })
        }
      </div>
    )
  }

  const steps = new Array(count).fill(0)
  const stepDoms = steps.map((item, index) => index === current ? 1 : 0)
  console.log(stepDoms);
  
  const renderStep = stepDoms.map((item, index) => (
    <div key={index} className={`${classPrefix}-step-item`}>
      {item === 1 && 
        <div className={`${classPrefix}-step-item ${classPrefix}-step-item-active`}></div>
      }
    </div>
  ))

  return (
    <div
      className={`${classPrefix}-container`}
    >
      {/* render carousel inner section */}
      {renderInner()}
      <div className={`${classPrefix}-step`}>
        {renderStep}
      </div>
    </div>
  )
})
