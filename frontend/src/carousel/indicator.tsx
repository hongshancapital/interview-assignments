import React, { CSSProperties, useState } from "react"

export interface IndicatorProps {
  // 该帧是否正在展示中
  isActive: boolean

  // 一帧的停留时间，单位: 毫秒
  stayTime: number
}

/**
 * 指示器， 屏幕下边表明共有多少帧，及当前在第几帧的横线
 */
export default function Indicator(props: IndicatorProps) {
  const { isActive, stayTime } = props
  const [ className, setClassName ] = useState('carousel-indicator')

  const style = {
    '--stayTime': `${stayTime}ms`
  } as CSSProperties

  setTimeout(() => {
    setClassName(`carousel-indicator ${isActive ? 'carousel-indicator-active' : 'carousel-indicator-inactive'}`)
  }, 0)

  return (
    <div
      style = {style}
      className={className}
    ></div>
  )
}
