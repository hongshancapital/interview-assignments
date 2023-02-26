import { ReactElement, ReactNode, useEffect, useState } from 'react'
import './carousel.css'

interface ICarouselProps {
  duration?: number,
  children: Array<ReactNode> | undefined
}

function Carousel({duration=3, children}: ICarouselProps): ReactElement {
  const [current, setCurrent] = useState<number>(-1)
  const total = children?.length || null

  useEffect(() => {
    if(!total) return
    if(current < 0) setCurrent(0)

    const timer = setTimeout(() => {
      let newcurrent = (current+1) % total
      setCurrent(newcurrent)
    }, duration * 1000)

    return () => timer && clearTimeout(timer)
  }, [current])

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

  return (
    <div className='j-carousel'>
      <div style={{left: `-${current * 100}%`}}>
        { total && children }
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