import React, { useEffect, useRef, useState } from 'react'
import { Outer, Inner } from './styles'

type IProps = {
  active: boolean
  /**
   * 单位：毫秒
   */
  duration: number
}

const Progress = (props: IProps) => {
  const { duration, active } = props
  const innerRef = useRef<HTMLDivElement>(null)
  const [offset, setOffset] = useState<string>('-100%')

  useEffect(() => {
    const ref = innerRef.current!
    ref.style.visibility = active ? 'visible' : 'hidden'
    setOffset(active ? '0' : '-100%')
  }, [active])

  return (
    <Outer>
      <Inner ref={innerRef} duration={duration} offset={offset} />
    </Outer>
  )
}

Progress.defaultProps = {
  active: false,
  duration: 3000
}

export default Progress
