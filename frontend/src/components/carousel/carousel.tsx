import { ReactElement, ReactNode, useEffect, useState } from 'react'
import './carousel.css'

interface ICarouselProps {
  duration?: number,
  children: Array<ReactNode> | undefined
}

function Carousel({duration=3, children}: ICarouselProps): ReactElement {
  const [current, setCurrent] = useState<number>(-1)
  const total = children?.length || 0

  const Dots: ReactNode = (
    <ul>
      {new Array(total).fill(null).map((_, index) =>
        <li key={index}
          className={index === current? 'active': ''}
          style={{ transition: `width ${duration}s linear`}}
        ></li>
      )}
    </ul>
  )

  useEffect(() => {
    if(!total) return
    setCurrent(0)

    const timer = setInterval(() => {
      setCurrent(current => (current+1) % total)
    }, duration * 1000)

    return () => clearInterval(timer)
  }, [])

  return (
    <div className='j-carousel'>
      <div style={{left: `-${current * 100}%`}}>
        { total>0 && children }
      </div>
      { total && Dots }
    </div>
  )
}

interface ICarouselItemProps {
  children: ReactNode
}

Carousel.Item = function({children}: ICarouselItemProps): ReactElement {
  return <div className='j-carousel-item'>{ children }</div>
}

export default Carousel