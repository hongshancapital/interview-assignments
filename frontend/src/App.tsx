import React, { useEffect, useRef } from 'react';
import './App.css';
import { Carousel, CarouselRefMethod } from './components/Carousel';
import { FullScreen } from './components/FullScreen';

const commonStyle = { fontSize: 32, fontWeight: 'bold', color: '#fff' };
function App() {

  const carousel = useRef<CarouselRefMethod>(null)

  useEffect(() => {
    setTimeout(() => {
      carousel.current?.play(); 
    }, 3000);
    setTimeout(() => {
      carousel.current?.pause();
    }, 5000);
  }, [])

  return (
    <div className="App">
      <Carousel ref={carousel} autoplay={false}>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#32bbeb' }}>
            Hello World
          </FullScreen>
        </Carousel.Item>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#45eebc' }}>
            Thank you for viewing my work
          </FullScreen>
        </Carousel.Item>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#bbddee' }}>
            Destined to meet for thousands of miles
          </FullScreen>
        </Carousel.Item>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#abadee' }}>
            Bye~
          </FullScreen>
        </Carousel.Item>
      </Carousel>
    </div>
  );
}

export default App;
