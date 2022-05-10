import React from "react"

interface Props {
  width: string
  height: string
  isActive: boolean
  duration: number
}

export function Progress(props: Props) {
  const { width, height, isActive, duration } = props

  return <div className="progress-container" data-testid="progress" style={{
    width,
    height
  }}>
    <div className={`progress ${isActive ? 'progress--active' : ''}`} style={{
      animationDuration: `${duration}s`
    }} />
  </div>
}
