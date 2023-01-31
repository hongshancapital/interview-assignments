import React from 'react';
import './App.css';
import Carousel from './components/Carousel';

function App() {
  const carouselItems: React.ReactNode[] = [
    <div>1</div>,
    <div>2</div>,
    <div>3</div>,
    <div>4</div>,
  ]

  return (
    <div className='App'>
      <Carousel
        autoPlay
        pauseDuringFocus
        items={carouselItems}
        style={{ height: 600 }}
      />
    </div>
  );
}

export default App;
