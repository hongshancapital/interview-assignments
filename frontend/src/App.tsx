import React from 'react';
import './App.css';
import { Carousel, CarouselItem } from './components/carousel';

function App() {
  return (
    <div className="App">
      <Carousel>
        <CarouselItem className={'example xphone'}>
          <div className={'title'}>xPhone</div>
          <div className={'description'}>Lots to love. Less to spend.<br />Starting at $399</div>
        </CarouselItem>
        <CarouselItem className={'example tablet'}>
          <div className={'title'}>Tablet</div>
          <div className={'description'}>Just the right amount of everything</div>
        </CarouselItem>
        <CarouselItem className={'example airpods'}>
          <div className={'title'}>But a Tablet or xPhone for college.<br />Get airPods.</div>
        </CarouselItem>
      </Carousel>
    </div>
  );
}

export default App;
