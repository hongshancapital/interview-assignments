import React, { useEffect, useRef, useState } from 'react'

import './Carousel.css'

interface CarouselProps {
  children?: React.ReactChild[],
  autoplayTime?: number,
  transitionTime?: number,
  infinity?: boolean,
  statusType?:string,
  showArrows?: boolean,
  showStatus?: boolean,
  onChange?: (index: number) => void,
  defaultIndex?: number,
}

const noop: () => void = () => {}

function Carousel ({
  children,

  autoplayTime = 2000,
  transitionTime = 500,

  showArrows = true,
  showStatus = true,
  infinity = true,
  statusType = 'bar',

  onChange = noop,

  defaultIndex = 0
}: CarouselProps) {
  const [targetOffset, setTargetOffset] = useState(0)

  const itemAmount: number = children?.length || 0

  // refs
  let containerRef = useRef<HTMLDivElement>(null)

  let currentIndex = useRef<number>(defaultIndex)
  let autoplayTimer = useRef<NodeJS.Timer>()

  let itemList: Array<any> = []
  let barList: Array<any> = []


  /**
   * Get carousel container current width
   */
  const getContainerWidth: () => number = function (): number {
    return containerRef.current?.offsetWidth || 0
  }

  /**
   * Swtich to target item, pass the item index
   * @param targetIndex index of the target item
   */
  const switchTo: (targetIndex: number) => void = function (targetIndex: number): void {
    if (infinity) {
      if (targetIndex >= 0) {
        targetIndex = targetIndex % itemAmount
      } else {
        targetIndex = itemAmount + (targetIndex % itemAmount)
      }
    }

    currentIndex.current = targetIndex
    setTargetOffset(currentIndex.current * getContainerWidth())

    onChange(currentIndex.current)
  }

  /**
   * Swtich to next item
   */
  const switchNext: () => void = function (): void {
    let targetIndex: number = currentIndex.current + 1
    if (infinity || targetIndex < itemAmount) {
      switchTo(targetIndex)
    }
  }

  /**
   * Swtich to previous item
   */
  const switchPrev: () => void = function (): void {
    let targetIndex: number = currentIndex.current - 1
    if (infinity || targetIndex >= 0) {
      switchTo(targetIndex)
    }
  }

  useEffect(() => {
    // reset time for manual switch condition
    if (autoplayTimer.current) {
      clearInterval(autoplayTimer.current)
    }

    // set autoplay timer
    if (autoplayTime > 0) {
      autoplayTimer.current = setInterval(() => {
        switchNext()
      }, autoplayTime + transitionTime)
    }

    return () => {
      clearInterval(autoplayTimer.current)
    }
  })

  children?.forEach((child, index) => {
    // generate carousel items list
    itemList.push(
      <li className="carousel-item" key={index}>
        {child}
      </li>
    )

    // generate status bar list
    let statusClass = `carousel-status-${statusType}`

    barList.push(
      index === currentIndex.current
        ? <span className={`carousel-status-item ${statusClass} carousel-current-item`} key={index}><i style={{ transition: `width ${autoplayTime}ms linear` }}></i>-</span>
        : <span className={`carousel-status-item ${statusClass}`} key={index} onClick={() => {switchTo(index)}}><i></i>-</span>
    )
  })

  return (
    <div className="carousel-container" ref={containerRef}>
      <div className="carousel-itemContainer">
        <ol className="carousel-itemList" style={{ transform: `translateX(-${targetOffset}px)`, transition: `transform ${transitionTime}ms` }}>
          {itemList}
        </ol>
      </div>

      {
        showArrows ? (
          <div className="carousel-arrowContainer">
            <div className="carousel-arrow carousel-arrow-prev" onClick={switchPrev}><i>&lt;</i></div>
            <div className="carousel-arrow carousel-arrow-next" onClick={switchNext}><i>&gt;</i></div>
          </div>
        ) : null
      }

      {
        showStatus ? (
          <div className="carousel-statusContainer">
            {barList}
          </div>
        ) : null
      }
    </div>
  )
}

export default Carousel