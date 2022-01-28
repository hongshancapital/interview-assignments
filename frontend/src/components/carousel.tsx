import React, { useEffect, useState } from 'react'
import { CarouselProps } from './interface'

/**
 * 轮播图控件
 */
export const Carousel = (arg: CarouselProps): JSX.Element => {
  
  let timer: number | null = null
  
  const [currentIndex, setCurrentIndex] = useState<number>(-1)

  arg = {
    content: arg.content,
    autoPlay: arg.autoPlay == null,
    delay: arg.delay || 3000,
    loop: arg.loop == null,
    dots: arg.dots || 'inside',
    onChange: arg.onChange
  }

  useEffect(() => {
    setTimeout(() => { startCount(0) }, 0)
    return () => {
      timer && clearTimeout(timer)
      timer = null
    } 
  }, []);

  const startCount = (i?: number) => {
    setCurrentIndex((cIndex) => {
      timer && clearTimeout(timer)
      timer = null
      let index: number
      if (i == null) {
        index = cIndex
        const picNum = arg.content.length - 1
        if (index < picNum) {
          index++
        } else if (index === picNum) {
          index = 0
        }
      } else {
        index = i
      }
      return index
    })
    timer = setTimeout(() => {
      startCount()
    }, arg.delay)
  }

  const { content, delay } = arg
  const translate3d = `translate3d(-${currentIndex * 100}%, 0px, 0px)`
  return (
    <div className="carousel-comp">
      <div className='carousel-comp-track' style={{ transform: translate3d }}>
        {
          content.map((item, index) => {
            return (<div className='carousel-item' key={index}>{item}</div>)
          })
        }
      </div>
      <div className="carousel-dots">
        {
          content.map((item, index) => {
            return <div className='carousel-dot' key={'dot' + index} onClick={startCount.bind(this, index)}>
              <div
                className={['carousel-dot-inside', currentIndex === index ? 'current-carousel-dot' : null].join(' ')}
                style={{ transitionDuration: currentIndex === index ? delay?.toString() + 'ms' : '0ms' }}></div>
            </div>
          })
        }
      </div>
    </div>
  )
}
