import { ReactElement, useEffect, useState } from 'react';
import './carousel.css'

interface IJSXChildren {
  children: ReactElement
}

interface ICarouselProps extends IJSXChildren {
  interval?: number
}

interface ICarouselItemProps extends IJSXChildren {
  
}

function Carousel({interval = 3, children}: ICarouselProps) {
  const [currentPage, setCurrentPage] = useState(-1)
  const totalPage = children?.props?.children?.length || null

  const run = () => {
    if(totalPage === null) return null
    if(currentPage < 0) setCurrentPage(0)
    return setInterval(() => {
      let newCurrentPage = (currentPage+1) % totalPage
      setCurrentPage(newCurrentPage)
    }, interval * 1000)
  }

  useEffect(() => {
    const intervaller = run()
    return () => {
      intervaller && clearInterval(intervaller)
    }
  })

  const totalArr = new Array(totalPage).fill(null)
  const TabBar = <ul>
    {totalArr.map((_,index) =>
      <li key={index}
        className={index === currentPage? 'active': ''}
        style={{ transition: `width ${interval}s linear`}}
      ></li>
    )}
  </ul>

  return <div className='j-carousel'>
      <div style={{left: `-${currentPage * 100}%`}}>
        { totalPage && children}
      </div>
      { totalPage? TabBar: null }
    </div>
}

Carousel.Item = function({children}: ICarouselItemProps) {
  return <div className='j-carousel-item'>{ children }</div>
}

export default Carousel;

