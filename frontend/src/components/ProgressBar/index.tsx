import React from "react"
import {Progress} from "./Progress"
import "./index.css"

interface Props {
  activeIndex: number
  gap: string
  height: string
  itemWidth: string
  count: number
  duration: number
}

export function ProgressBar(props: Props) {
  const {activeIndex, count, height, itemWidth, gap, duration} = props

  const inAscendingOrder = Array.from(Array(count).keys())

  return <div style={{
    height,
    gridColumnGap: gap,
    gridTemplateColumns: `repeat(${count}, ${itemWidth})`
  }} className="progress-bar">
    {
      inAscendingOrder.map(index => <Progress
        key={index}
        height={height}
        width={itemWidth}
        isActive={index === activeIndex}
        duration={duration} />
      )
    }
  </div>
}
