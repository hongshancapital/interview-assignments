import React, {useState, useEffect, FC, useRef, CSSProperties} from 'react'
import { ICarouselProps } from './interface'
import Pagination from './Pagination'
import './Carousel.css'
import useInterval from './useInterval'


const Carousel:FC<ICarouselProps> = (props) => {
  const {children, showPagination, delay, duration, className} = props
  let count = React.Children.count(children)

  const [active, setActive] = useState(0)
  const [itemWidth, setItemWidth] = useState<number>(0)
  const wrapperRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if(wrapperRef.current){
      setItemWidth(wrapperRef.current.clientWidth)
    }
  },[])

  useInterval(()=>{
    goNext(active+1)
  }, delay)

  const goNext = (nextActive:number) => {
    nextActive = nextActive >= count ? 0 : nextActive
    setActive(nextActive)
  }

  const wrapperStyle:CSSProperties = {
    width: 100 * count + '%',
    transform: `translateX(${-itemWidth * active}px)`,
    transitionDuration: `${duration}ms`
  }

  const renderPagination = () => {
      return showPagination ? <Pagination count={count} delay={delay} active={active} duration={duration} onClick={goNext}/> : null
  }

  return (
    <div className={`${className} container`} ref={wrapperRef}>
      <div className='carousel-wrapper' style={wrapperStyle}>
        {React.Children.map(children, ((child,index) => {
          return <div className='carousel-item'>
            {child}
          </div>
        }))}
      </div>
      {renderPagination()}
    </div>
  )
}
export default Carousel