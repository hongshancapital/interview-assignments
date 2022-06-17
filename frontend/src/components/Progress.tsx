import React, { useRef, useEffect } from 'react'
import './Progress.scss'

interface Props {
  stepTime?: number
  theme?: Theme
  active?: boolean
  handleEnd?: Function
  onClick?: Function
  index?: number
}

function Progress(props: Props) {
  const progressInner = useRef<HTMLDivElement | null>(null)
  let startTime: number | undefined = undefined
  let refStep: number | null = null
  const setStyle = (percent: number = 0) => {
    progressInner.current &&
      (progressInner.current.style.transform = `translateX(${percent - 100}%)`)
  }
  const reset = () => {
    setStyle()
    startTime = undefined
    refStep && window.cancelAnimationFrame(refStep)
  }
  const step = () => {
    if (!props.active) {
      return reset()
    }
    if (startTime === undefined) {
      startTime = Date.now()
    }
    const elapsed = Date.now() - startTime
    const runTime = (props.stepTime || 3) * 1000
    if (elapsed < runTime) {
      const percent = Math.ceil((elapsed / runTime) * 100)
      setStyle(percent)
      refStep = window.requestAnimationFrame(step)
    } else {
      reset()
      props.handleEnd && props?.handleEnd()
    }
  }
  useEffect(() => {
    if (props.active) {
      step()
    }
    return () => {
      reset()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.active])

  return (
    <div
      onClick={() => props.onClick && props.onClick()}
      className={`progress ${props.theme || 'light'}`}
    >
      <div ref={progressInner} className="progress-inner"></div>
    </div>
  )
}

export default Progress
