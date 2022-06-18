import React, { memo, useState, useEffect } from 'react'
import Core from './core/index'
import { corePluginFactory } from './plugin'
import { useDot, useDragger } from './hooks'
import { useOnce } from '../../utils/share'
import type { PropsWithChildren, FunctionComponent } from 'react'
import { CarouselProps } from './type'
import './index.css'

const ITEM_NAME = 'CarouselItem'

export default function Carousel (props: PropsWithChildren<CarouselProps>) {
  const {
    interval = 2500,
    stepTime = 500,
    dots = true,
    dotPosition = 'bottom',
    dotJump = true,
    dragable = true
  } = props

  // CarouselItem only
  let items: React.ReactNode[] = []
  if (Array.isArray(props.children)) {
    items = props.children.filter(item => item.type.displayName === ITEM_NAME)
  }

  // core plugin & init logic
  const [translate, setTranslate] = useState(-100)
  const coreData = useOnce(() => {
    let fixStep = dragable ? 1 : 0
    const { data, plugin } = corePluginFactory({
      interval,
      stepTime,
      setTranslate (index) {
        const value = -(index + fixStep) * 100
        setTranslate(value)
      }
    })
    const carousel = new Core(plugin)
    carousel.init(items.length)
    return {
      ...data,
      carousel,
      enableDragger (enable: boolean) {
        fixStep = dragable ? 1 : 0
      }
    }
  })

  // enable dragger for setTranslate
  useEffect(() => {
    coreData.enableDragger(dragable)
  }, [dragable, coreData])

  // dot plugin
  const dotData = useDot({
    enableDot: !!dots,
    dotJump,
    carousel: coreData.carousel
  })

  // dragger hook
  const draggerData = useDragger({
    enableDrag: dragable,
    carousel: coreData.carousel
  })

  // carousel effect
  useEffect(() => {
    coreData.carousel.mount()
    return () => {
      coreData.carousel.unmount()
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  // 使用事件代理，其实对于 react 而言必要性大不。但是从数据层面，至少降低了函数数量
  const dotJumpHandler = (e: React.MouseEvent) => {
    const dataset = (e.target as HTMLElement).dataset
    const index = dataset?.index
    index && coreData.carousel.jump(+index)
  }

  const fill = items.length && dragable

  const style = draggerData.translate === null
    ? {
        transform: `translate(${translate}%, 0)`,
        transition: `transform ${stepTime / 1000}s linear`
      }
    : {
        transform: `translate(${draggerData.translate}px, 0)`
      }
  return (
    <div
      className="carousel-container"
    >
      <div
        className="carousel-content"
        style={style}
        onTransitionEnd={coreData.transitionEnd}
        {...draggerData.events}
      >
        {fill && items[items.length - 1]}
        {items}
        {fill && items[0]}
      </div>
      {dots && <div
        className={`carousel-dots carousel-dots-${dotPosition}`}
        onClick={dotJumpHandler}
      >
        {
          items.map(
            (_, i) =>
              <div
                className="carousel-dot"
                key={i}
                data-index={i}
              >
                <div className="carousel-dot-progress" style={{ width: `${dotData.getProgress(i)}%` }}></div>
              </div>
          )
        }
      </div>}
    </div>
  )
}

const CarouselItem: FunctionComponent<{}> = memo((props) => {
  return (
    <div className="carousel-item">{props.children}</div>
  )
})

CarouselItem.displayName = ITEM_NAME

export { CarouselItem }
