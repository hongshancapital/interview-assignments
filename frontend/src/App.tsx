/* eslint-disable @typescript-eslint/no-unused-vars */
import { ElementRef, useRef } from 'react';
import Carousel from './components/Carousel';
import './App.scss';

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
      <Carousel height="100vh" ref={carouselRef} autoplay={false}>
        <Carousel.Item>
          <div className='goods-item phone'>
            <h2 className='title'>xPhone</h2>
            <p className='desc'>Lots to love. Less to spend.<br/> Starting at $399.</p>
          </div>
        </Carousel.Item>
        <Carousel.Item>
          <div className='goods-item tablet'>
            <h2 className='title'>Tablet</h2>
            <p className='desc'>Just the right amount of everything.</p>
          </div>
        </Carousel.Item>
        <Carousel.Item>
          <div className='goods-item airpods'>
            <p className='desc'>Buy a Tablet or xPhone for college.<br /> Get AirPods.</p>
          </div>
        </Carousel.Item>
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
