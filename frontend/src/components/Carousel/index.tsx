import React from 'react';
import CarouselItem from './CarouselItem';
import CarouselDots from './CarouselDots';
import './Carousel.d.ts';
import './index.css';

const Carousel: React.FC<CarouselData> = ({ datasource, duration }) => {
  const [index, setIndex] = React.useState(0);
  const contentStyle = {
    transform: `translateX(${(index * -100) + '%'})`
  }
  const nextPage = () => {
    setIndex((index + 1) % datasource.length)
  };
  return (
    <div className='carousel'>
      <div className='carousel-content' style={contentStyle}>
        {
          datasource.map((v, i) => <CarouselItem data={v} key={i}></CarouselItem>)
        }
      </div>
      <CarouselDots itemCount={datasource.length} duration={duration} onIndex={index} onAnimationEnd={nextPage}></CarouselDots>
    </div>
  )
}

export default Carousel 