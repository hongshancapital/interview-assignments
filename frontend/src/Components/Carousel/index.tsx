import React, { memo } from 'react'
import { useCarousel, useDot, useDragger } from './hooks'
import { noop } from '../../utils/share'
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

  const carouselData = useCarousel({
    interval,
    length: items.length
  })

  const dotData = useDot(
    { time: interval, enableDot: !!dots, dotJump },
    carouselData
  )

  const draggerData = useDragger({
    enableDrag: dragable,
    length: items.length
  }, carouselData)

  // 使用事件代理，其实对于 react 而言必要性大不。但是从数据层面，至少降低了函数数量
  const dotJumpHandler = dots
    ? (e: React.MouseEvent) => {
        const dataset = (e.target as HTMLElement).dataset
        const index = dataset?.index
        index && carouselData.jump(+index)
      }
    : noop

  const fill = items.length && dragable

  const fixStep = dragable ? 1 : 0
  const style = draggerData.translate === null
    ? {
        transform: `translate(${(carouselData.target + fixStep) * -100}%, 0)`,
        transition: `transform ${stepTime / 1000}s linear`
      }
    : { transform: `translate(${draggerData.translate}px, 0)` }

  return (
    <div
      className="carousel-container"
    >
      <div
        className="carousel-content"
        style={style}
        onTransitionEnd={carouselData.transitionEnd}
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
