import React, {
  useState,
  useLayoutEffect,
  useMemo,
  useRef,
  useCallback
} from 'react'
import Styles from './styles.module.css'
import { ICarouselProps } from './interface'
import { validateAndGetChildren } from '../../utils'
import { staged } from 'staged-components'
import CarouselItem from './carousel-item'
import Indicator from './indicator'
const DEF_CONFIG = {
  // 间隔单位: ms
  delay: 2000,
  // 自动播放
  autoPlay: true,
  // 间隔单位: ms
  loop: true
}
const Carousel = staged((props: ICarouselProps) => {
  props = Object.assign(DEF_CONFIG, props)
  const { delay = 0, autoPlay } = props
  const [currStep, setCurrStep] = useState(0)
  const [actionTable] = useState([0, 8, 2 * 8])
  const wraperElem = useRef<HTMLDivElement>(null)
  const outerElem = useRef<HTMLUListElement>(null)
  const validChildren = useMemo(() => {
    const validChildren = validateAndGetChildren({
      children: props.children,
      targetElement: CarouselItem,
      errMsg: '发生错误：子节点必须是CarouselItem'
    })
    return validChildren
  }, [props.children])

  const moveToStep = useCallback(
    (currStep: number) => {
      if (wraperElem && wraperElem.current) {
        wraperElem.current.style.transform = `translateX(-${actionTable[currStep]}rem)`
        wraperElem.current.style.transition = `${delay / 2}ms ease-in-out`
        setCurrStep(currStep)
      }
    },
    [delay, actionTable]
  )
  let timerIdRef = useRef(0)

  const moveToNext = useCallback(
    (
      currStep: number,
      wraperElem: React.RefObject<HTMLDivElement>,
      autoPlay?: Boolean
    ) => {
      if (autoPlay) {
        timerIdRef.current && clearTimeout(timerIdRef.current)
        timerIdRef.current = window.setTimeout(() => {
          const step = (currStep + 1) % actionTable.length
          moveToStep(step)
        }, delay)
      }
    },
    [delay, actionTable, moveToStep, timerIdRef]
  )
  useLayoutEffect(() => {
    if (!outerElem.current) return
    moveToNext(currStep, wraperElem, autoPlay)
  }, [currStep, autoPlay, moveToNext])

  const handleClickStep = (i: number) => {
    window.clearTimeout(timerIdRef.current)
    moveToStep(i)
  }
  return (
    <>
      <ul
        data-testid='Carousel'
        {...props}
        className={`${Styles.Carousel} ${props.className}`}
        ref={outerElem}
      >
        <div ref={wraperElem} className={Styles.CarouseWraper}>
          {props.children}
        </div>
        <Indicator
          className={Styles.indicator}
          total={validChildren.length}
          currIndex={currStep}
          delay={delay}
          onClick={handleClickStep}
        />
      </ul>
    </>
  )
})
export default Carousel
