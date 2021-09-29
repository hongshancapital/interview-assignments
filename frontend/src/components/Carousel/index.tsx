import React from 'react';
import CarouselItem from './CarouselItem';
import CarouselDots from './CarouselDots';
import './Carousel.d.ts';
import './index.css';

const Carousel: React.FC<CarouselData> = ({ datasource, duration }) => {
  const items = datasource.map((v, i) => <CarouselItem data={v} key={i}></CarouselItem>);
  const [index, setIndex] = React.useState(0);
  
  const contentStyle = {
    transform: `translateX(${(index * -100) + '%'})`
  }
  setTimeout(() => {
    setIndex((index + 1) % datasource.length)
  }, duration);
  return (
    <div className='carousel'>
      <div className='carousel-content' style={contentStyle}>{items}</div>
      <CarouselDots itemCount={items.length} duration={duration} onIndex={index}></CarouselDots>
    </div>
  )
}

export default Carousel 