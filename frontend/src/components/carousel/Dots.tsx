import React, { useMemo, useEffect, useState } from 'react'
import './Dots.css'

interface DotsProps {
  activeIndex: number
  count: number
  onClick: (index: number) => void
  duration?: number
}

export const Dots = (props: DotsProps) => {
  const { count, activeIndex, onClick, duration } = props
  const [fillWidthMap, setFillWidthMap] = useState(new Array(count).fill('0'))

  useEffect(() => {
    if (!duration) return
    setFillWidthMap(
      fillWidthMap.map((item, index) => (index === activeIndex ? '100%' : '0'))
    )
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeIndex, duration])

  const renderItems = useMemo(() => {
    const result: React.ReactNode[] = []
    for (let key = 0; key < count; key++) {
      result.push(
        <div
          key={key}
          className={`carousel-dots__item ${
            key === activeIndex ? `carousel-dots__item--active` : ''
          }`}
          onClick={() => {
            if (activeIndex !== key) {
              onClick(key)
            }
          }}
        >
          <div
            className={`carousel-dots__item--fill ${
              duration && 'carousel-dots__item--move'
            }`}
            style={{
              width: fillWidthMap[key],
              transitionDuration: `${
                duration && duration / 1000
              }s`,
            }}
          ></div>
        </div>
      )
    }

    return result
  }, [activeIndex, count, duration, fillWidthMap, onClick])

  return (
    <div className="carousel-dots">
      <div className="carousel-dots__content">{renderItems}</div>
    </div>
  )
}
