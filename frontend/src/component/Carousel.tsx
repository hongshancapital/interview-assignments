import React from 'react';
import { useEffect, useState, useRef } from 'react';
import {CarouselProps} from './interface'
import './Carousel.css';

function Carousel({duration = 3000, carouselList = [], width=0, height=0}: CarouselProps) {
  const [active, setActive] = useState(1);
  const container = useRef<HTMLDivElement|null>(null);
  const activeDot = 'carousel-dot-active';
  const hiddenDot = 'carousel-dot-hide';

  if(!width) {
    width = window.screen.width;
  }
  if (!height) {
    height = window.screen.height - 140;
  }

  const len = carouselList.length;
  const boxWidth = width * len;

  const caurselItem = carouselList.map((item, index) => (
    <div 
      className='carousel-item'
      style={{ width: `${width}px`, height: `${height}px`, left: `${index * width}px`,backgroundColor: `${item.background}`}} 
      key={index}>
      <div className='carousel-item-content'>
        <div className='carousel-content-text' style={{color: `${item.color}`}}>
          {item.title}
        </div>
        <div className='carousel-item-image'>
          <img className='carousel-image-image' src={item.src} alt={item.title}></img>
        </div>
      </div>
    </div>
  ));

  const caurselDot = carouselList.map((_, index) => (
    <div className='caursel-dot-content' key={index}>
      <div
        className={`caursel-dot-item ${index === active-1 ? activeDot: hiddenDot}`}
        style={{ transitionDuration: `${duration + 2000}`}}>
      </div>
    </div>
  ));

  const setTransition = () => {
    const distance = (1 - active) * width;
    if(container && container.current && container.current['style']) {
      container.current.style.transform = `translate(${distance}px, 0)
    `;
      container.current.style.transitionDuration = `${duration}`;
    }
  };

  // 下一页
  const handleNext = () => {
    // 对临界值进行处理
    setActive(active === len ? 1 : active + 1);
  };

  useEffect(() => {
    setTransition();
    var timer = window.setTimeout(()=>{
      handleNext();
    }, duration)
    return function clearTimer () {
      window.clearTimeout(timer)
    }
  });



  return (
    <div>
      <div
        className='carousel'
        style={{ width: `${width}px`, height: `${height}px` }}>
        <div
          ref={container}
          className='carousel-box'
          style={{ width: `${boxWidth}px`, height: `${height}px`}}>
          {caurselItem}
        </div>
        <div className='clear'></div>
        <div className='carousel-dot'>
          <div className='carousel-dot-content'>{caurselDot}</div>
        </div>
      </div>
    </div>
  );
}

export default Carousel;
