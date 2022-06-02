import React from 'react';
import './App.css';
import { Carousel } from './components/Carousel';
import { CarouselList } from './constants/carouselList';

function App() {
  return (
    <div className="App">
      <Carousel images={CarouselList} />
    </div>
  );
}

export default App;
