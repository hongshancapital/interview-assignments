import React from 'react';
import './App.css';

import Carousel from './components/Carousel/Carousel';
import SlideOne from './components/Carousel/carouselSlides/SlideOne';
import SlideTwo from './components/Carousel/carouselSlides/SlideTwo';
import SlideThree from './components/Carousel/carouselSlides/SlideThree';

function App() {
  return (
    <div className="App">
      <Carousel autoPlay={30}>
        <SlideOne></SlideOne>
        <SlideTwo></SlideTwo>
        <SlideThree></SlideThree>
      </Carousel>
    </div>
  );
}

export default App;
