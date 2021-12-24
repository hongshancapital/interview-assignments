import React, { useEffect, useMemo, useRef, useState } from 'react';
import './Carousel.scss';
import { CarouselContainer, CarouselWrap, PaginationInner } from './styles';

function Carousel(props: any) {
  const [index, setIndex] = useState(0);
  const intervalRef = useRef<NodeJS.Timeout>();
  
  const paginationList = useMemo(() => {
    const list = [];
    for (let i = 0; i < props.children.length; i++) {
      list.push(i)
    }
    return list;
  }, [props.children.length])
  useEffect(() => {
    setIndex(i => (i + 1) % props.children.length);
    intervalRef.current = setInterval(() => {
      setIndex(i => (i + 1) % props.children.length)
    }, props.delay);
    return () => {
      intervalRef.current && clearInterval(intervalRef.current);
    }
  }, [props.delay, props.children.length])
  return (
    <CarouselContainer {...props} className="carousel">
      <CarouselWrap {...props} className='wrap' style={{ transform: 'translate3d(' + -index * props.width + 'px, 0, 0)' }}>
        {props.children}
      </CarouselWrap>
      <ul className='pagination'>
        {
          paginationList.map(item => {
            return (
              <li key={item} className={index === item ? 'active' : 'inactive'}>
                <PaginationInner {...props}></PaginationInner>
              </li>
            )
          })
        }
      </ul>
    </CarouselContainer>
  )
}

export default Carousel;