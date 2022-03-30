import React, { useState, useEffect, useRef } from 'react'

import './Carousel.css'

interface Props {
  children: React.ReactNode
  autoplayTime?: Number
  transitionTime?: Number
}

const useInterval = (callback: Function, delay?: number | null) => {
  const savedCallback = useRef<Function>(() => {})

  useEffect(() => {
    savedCallback.current = callback
  })

  useEffect(() => {
    if (delay !== null) {
      const interval = setInterval(() => savedCallback.current(), delay || 0)
      return () => clearInterval(interval)
    }

    return undefined
  }, [delay])
}

const Carousel = (props: Props) => {
  const children = props.children
  const length = children
    ? Array.isArray(children)
      ? (children as Array<React.ReactNode>).length
      : 0
    : 0

  let touch = false

  const autoplayTime = props.autoplayTime ? props.autoplayTime : 3000

  const transitionTime = props.transitionTime ? props.transitionTime : 0.6

  const [itemIndex, setItemIndex] = useState(0)
  const [dotIndex, setDotIndex] = useState(0)

  const onMouseOver = () => {
    touch = true
  }

  const onMouseOut = () => {
    touch = false
  }

  useInterval(() => {
    if (touch) return
    setDotIndex(dotIndex + 1)

    if (dotIndex >= 100) {
      setDotIndex(0)
      if (itemIndex >= length - 1) {
        setItemIndex(0)
      } else {
        setItemIndex(itemIndex + 1)
      }
    }
  }, (autoplayTime as number) / 100)

  return (
    <div className='carousel' onMouseOver={onMouseOver} onMouseOut={onMouseOut}>
      <div
        className='carousel__container'
        style={{
          left: `-${itemIndex * 100}%`,
          width: length * 100 + '%',
          transition: `all ${transitionTime}s`
        }}
      >
        {children}
      </div>
      <ul className='carousel__container__dots'>
        {new Array(length).fill(0).map((v, i) => {
          return (
            <li key={i}>
              {i === itemIndex ? (
                <div
                  style={{
                    width: dotIndex + '%'
                  }}
                ></div>
              ) : null}
            </li>
          )
        })}
      </ul>
    </div>
  )
}

export default Carousel
