/* eslint-disable @typescript-eslint/no-unused-vars */
import { ElementRef, useRef } from 'react';
import Carousel from './components/Carousel';
import './App.scss';

const CAROUSEL_DATA = [
  {
    title: 'xPhone',
    desc: ['Lots to love.Less to spend.', 'Starting at $399.'],
    className: 'phone',
  },
  {
    title: 'Tablet',
    desc: ['Just the right amount of everything.'],
    className: 'tablet',
  },
  {
    title: null,
    desc: ['Buy a Tablet or xPhone for college.', 'Get AirPods.'],
    className: 'airpods',
  },
];

function App() {
  const carouselRef = useRef<ElementRef<typeof Carousel>>(null);

  const handlePrev = () => {
    carouselRef.current?.prev(false)
  }

  const handleNext = () => {
    carouselRef.current?.next(true)
  }

  const handleGotoLast = () => {
    carouselRef.current?.goTo(2, false);
  }

  return (
    <div className='App'>
      <Carousel height="100vh" ref={carouselRef}>
        {CAROUSEL_DATA.map(({ title, desc, className }, index) => (
          <Carousel.Item key={index}>
            <div className={`goods-item ${className}`}>
              {title && (
                <h2 className='title'>{title}</h2>
              )}
              {desc.map((str, _index) => (
                <p key={_index} className='desc'>{str}</p>
              ))}
            </div>
          </Carousel.Item>
        ))}
      </Carousel>

      {/* <div className='control-wrapper'>
        <button className='control-item' onClick={handlePrev}>上一页</button>
        <button className='control-item' onClick={handleNext}>下一页</button>
        <button className='control-item' onClick={handleGotoLast}>去最后一页</button>
      </div> */}
    </div>
  );
}

export default App;
