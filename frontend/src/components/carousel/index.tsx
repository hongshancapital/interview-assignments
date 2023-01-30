// namespace: mp-carousel
import React, { useRef, useLayoutEffect, useState, ReactNode, MutableRefObject } from 'react';
import type { CarouselProps, CarouselBaseState, CarouselMemoElsResponse, MemoState, CarouselRecordStatus,MarksProps } from './types'
import './index.scss'
const joinClass = (...rest: string[]) => rest.join(' ')

// 缓存轮播时的列表元素以及获取最外层容器宽度
const useMemoEls = (parentRef: MutableRefObject<HTMLDivElement | null>, children: CarouselProps['children']): CarouselMemoElsResponse => {
  const [res, setRes] = useState<MemoState>({ items: [], containerWidth: 0 })

  useLayoutEffect(() => {
    const {current} = parentRef as MutableRefObject<HTMLDivElement>
    // 监听最外层容器尺寸变更
    let parentObserver = new ResizeObserver((entries) => {
      entries.forEach(calcElementAndwidth);
    })
    parentObserver.observe(current)

    // 收集列表元素集和外层容器宽度
    const calcElementAndwidth = () => {
      const { width } = getComputedStyle(current)

      // // 外层容器高度或宽度为0时，无需缓存列表元素
      // if (parseInt(height) === 0 || parseInt(width) === 0) return
      setRes(
        {
          items: children.map((el, i) => {
            return <div key={'mp-carousel-item' + i} className='mp-carousel-item-container-item' >
              {el}
            </div>
          }),
          containerWidth: parseFloat(width)
        }
      )
    }
    calcElementAndwidth()

    return () => parentObserver.unobserve(current)

  }, [children,])
  return res
}
const defaultProps: CarouselProps = {
  children: [],
  interval: 2000,
  className: ''
}
const Carousel = (props: CarouselProps) => {
  const { children, interval, className, ...restProps } = props
  const parentRef = useRef<HTMLDivElement | null>(null)
  // 创建全局变量，用于记录定时器,单次间隔开始时间,用户hover操作的时间
  const recordStatus = useRef<CarouselRecordStatus>(
    {
      timer: 0,
      loopStartTime: 0,
      hoverStartTime: 0,
    }
  ).current

  const [state, setState] = useState<CarouselBaseState>({
    curIndex: 0,
    status: 'loop',
    listLen: children.length
  })

  // 开始缓存轮播时的列表元素
  const elsObj = useMemoEls(parentRef, children)

  useLayoutEffect(() => {
    function startLoop(count = state.curIndex) {
      recordStatus.timer = setInterval(
        () => {
          // 记录单次间隔的时间起点
          recordStatus.loopStartTime = performance.now()
          setState({
            ...state, curIndex: ++count % state.listLen
          })
        },
        interval)
    }

    // 有效列表长度小于2，不做轮播处理
    if (elsObj.items.length > 1) {
      switch (state.status) {
        case 'leave':
          let subTime = interval - (recordStatus.hoverStartTime - recordStatus.loopStartTime)
          if (subTime > 0) {
            // 避免一个间隔内多次触发
            clearTimeout(recordStatus.timer)
            recordStatus.timer = setTimeout(() => {
              recordStatus.loopStartTime = performance.now()
              let curIndex = (state.curIndex + 1) % state.listLen
              setState({ ...state, curIndex })
              startLoop(curIndex)
            }, subTime);
          }
          break
        case 'hover':
          // 记录鼠标进入时间
          recordStatus.hoverStartTime = performance.now()
          clearInterval(recordStatus.timer)
          break
        case 'loop':
          startLoop()
          break
        default: break
      }
      return () => {
        clearInterval(recordStatus.timer)
      }
    }
  }, [state.status, elsObj.items.length])

  const onCarouselClick = ({ target }: React.SyntheticEvent<HTMLDivElement>) => {
    let mpCarouselMarkIndex = (target as HTMLDivElement).dataset.mpCarouselMarkIndex
    if (typeof mpCarouselMarkIndex !== 'undefined' && +mpCarouselMarkIndex !== state.curIndex)
      setState({
        ...state,
        curIndex: +mpCarouselMarkIndex
      })
  }
  return <div
    ref={parentRef}
    className={joinClass('mp-carousel-container', className || '')}
    onClick={onCarouselClick}
    onMouseEnter={() => setState({ ...state, status: 'hover' })}
    onMouseLeave={() => setState({ ...state, status: 'leave' })}
    {...restProps}
  >

    <div className='mp-carousel-item-container'
      style={{ transform: `translateX(-${state.curIndex * elsObj.containerWidth}px)` }}>
      {elsObj.items}

    </div>
    <Marks curIndex={state.curIndex} listLen={state.listLen} />
  </div>
}
// ------Marks Component
const createMarkClass = (isEq: boolean) => {
  return 'mp-carousel-mark-container-item ' + (isEq ? ' mp-carousel-mark-container-item--active' : '')
}

const Marks = ({ curIndex, listLen }:MarksProps) => {
  if (listLen <= 1) return null

  let children: ReactNode[] = []
  for (let index = 0; index < listLen; index++) {
    children.push(
      <div key={index} data-mp-carousel-mark-index={index} role='mark'
        className={createMarkClass(curIndex === index)}>
        <div className='mp-carousel-mark-container-item-mask'></div>
      </div>
    )
  }
  return <div className='mp-carousel-mark-container'>
    {children}
  </div>
}
Carousel.defaultProps = defaultProps
export default Carousel