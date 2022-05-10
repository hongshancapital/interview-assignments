import React, { useState, ReactElement } from "react"
import { useInterval } from './hooks'
import { ItemWrapper } from './ItemWrapper'
import { ProgressBar } from "../ProgressBar";

interface Props {
  items: Array<ReactElement>
  width: string
  height: string
  duration?: number
  progressGap?: string
  progressWidth?: string
  progressHeight?: string
  progressToBottom?: string
  transitionDuration?: number
}

const defaultProps = {
  duration: 2,
  transitionDuration: .5,
  progressGap: '8px',
  progressWidth: '30px',
  progressHeight: '2px',
  progressToBottom: '60px'
}

export function Carousel(props: Props) {
  const {
    width,
    height,
    items,
    duration,
    transitionDuration,
    progressGap,
    progressWidth,
    progressHeight,
    progressToBottom
  } = {
    ...defaultProps,
    ...props
  }

  const length = items.length
  const [index, setIndex] = useState(0)

  useInterval(() => {
    calcIndex()
  }, duration * 1000)

  function calcIndex() {
    if (index === length - 1) {
      setIndex(0)
    } else {
      setIndex(index + 1)
    }
  }

  return <div className="carousel" style={{ width, height }}>
    <div className="item-container" style={{
      transform: calcTranslateX(index, length),
      transitionDuration: `${transitionDuration}s`
    }}>
      { items.map((item, index) => <ItemWrapper width={width} height={height} key={index}>{ item }</ItemWrapper>) }
    </div>
    <div className="bar-container" style={{
      bottom: progressToBottom,
    }}>
      <ProgressBar
        activeIndex={index}
        count={length}
        gap={progressGap}
        height={progressHeight}
        duration={duration}
        itemWidth={progressWidth}
      />
    </div>
  </div>
}

function calcTranslateX(index: number, length: number): string {
  return `translateX(-${index * 100 / length}%)`
}
