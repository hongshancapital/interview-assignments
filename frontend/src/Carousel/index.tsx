import React, { useState, useEffect, useRef } from 'react';
import './index.css';

export interface SlideBarProps {
  speed?: number,
  count: number,
  current: number,
  className?: string,
}

export interface SlideListProps {
  children?: React.ReactNode,
  current: number,
  className?: string,
  direct?: string,
}


export interface CarouselProps {
  speed?: number,
  children?: React.ReactNode
  listClassName?: string,
  barClassName?: string,
}

export const SlideList = ({
  children,
  current = 0,
  className = '',
  direct = 'right',
}: SlideListProps) => {

  const activeStyle = {
    transitionDuration: '.5s',
  }

  const count = React.Children.count(children);

  const previous = (current - 1 + count) % count;

  const calDirect = (current: number, index: number, count: number) => {
    if (current === index) {
      return 'active';
    }

    // if current is first one, change the last one's direct
    if (current === 0 && index === count - 1) {
      return direct === 'left'? 'right' : 'left';
    }

    if (current > index) {
      return direct === 'left'? 'right' : 'left';
    }

    return direct;
  }

   
  return (
    <div className={`slidelist-container ${className}`}>
      { React.Children.map(children, (child, index) => (
        <div
          key={index}
          className={`slidelist-item ${calDirect(current, index, count)}`}
          style={
            index === current || index === previous? activeStyle : {}
          }
        >
          {child}
        </div>
      )) }
    </div>
  )
}

export const SlideBar = ({
  speed = 5,
  count,
  current,
  className = '',
}: SlideBarProps) => {

  // create array [0, 1, 2, ..., count - 1]
  const items = Array.from({length: count}, (v, k) => k);

  return (
    <div className={`slidebar-container ${className}`}>
      { items.map(value => (
        <div key={value} className="slidebar-item">
          <div
            className={`slidebar-progress ${current === value ? 'active' : ''}`}
            style={{
              animationDuration: `${speed}s`
            }}
          />
        </div>
      )) }
    </div>
  )
}

export const Carousel = ({
  speed = 5,
  listClassName = '',
  barClassName = '',
  children
}: CarouselProps) => {

  const [currentIndex, setCurrentIndex]=useState(0); // current id is showing
  const [currentDirect, setCurrentDirect] = useState('right'); // from right to left

  const timerRef: {current: NodeJS.Timeout | null}  = useRef(null);
  const tickRef: {current: Function } = useRef(() => false);

  const count = React.Children.count(children);
  
  tickRef.current = () => {
    timerRef.current = setTimeout(() => {

      const nextIndex = (currentIndex + 1) % count;
      if (nextIndex === 0) {
        const nextDirect = currentDirect === 'left' ? 'right' : 'left';
        setCurrentDirect(nextDirect)
      }
      setCurrentIndex(nextIndex);
      tickRef.current();
    }, speed * 1000)
  }
  
  useEffect(() => {
    tickRef.current()
    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
      }
    }
  }, [])

  return (
    <div className="carousel-container">
      <SlideList
        current={currentIndex}
        className={listClassName}
        direct={currentDirect}
      >
        { children }
      </SlideList>
      <SlideBar
        count={count}
        current={currentIndex}
        className={barClassName}
        speed={speed}
      />
    </div>
  )
}

export default Carousel;
