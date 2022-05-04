import React, { useRef } from 'react'
import Track from './track'
import Slick from './slick'
import './index.scss'
import useStep from './hook/useStep'
import type { ICarousel } from './types'

function Carousel(_props: ICarousel) {
  const { duration = 3000, children } = _props
  const len = Array.isArray(children) ? children.length : 1
  const [step] = useStep({
    duration,
    lastStep: len - 1,
  })
  const ref = useRef<HTMLDivElement>(null)

  return (
    <div ref={ref} className="carousel-lite">
      <Track step={step} width={ref.current?.getBoundingClientRect().width || 0}>
        {_props.children}
      </Track>
      <Slick
        count={len}
        step={step}
        duration={duration}
      />
    </div>
  )
}

export default Carousel
