import React, { useEffect, useRef } from 'react'
import type { ReactNode } from 'react'

function normalizeChildren(children: ReactNode | ReactNode[]) {
  let _children = []
  if (!Array.isArray(children))
    _children.push(children)
  else
    _children = children

  return _children.map((child, ind) => {
    return (
      <div key={ind} className="carousel-lite__slide">{child}</div>
    )
  })
}

interface ITrack {
  children: ReactNode | ReactNode[]
  step: number
  width: number
}

function Track(_props: ITrack) {
  const slidesRef = useRef<HTMLDivElement>(null)
  const childrenNodes = normalizeChildren(_props.children)

  useEffect(() => {
    if (!slidesRef.current)
      return
    const { step, width } = _props

    slidesRef.current!.style.transform = `translateX(-${step * width}px)`
  }, [_props.step])

  return (
    <div ref={slidesRef} className="carousel-lite__slides">
      {childrenNodes}
    </div>
  )
}

export default Track
