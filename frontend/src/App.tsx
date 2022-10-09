import React from 'react';

import './App.scss';
import Carousel from './components/Carousel';
import mockCarouselData from './mock/carousel';

function App() {
  return (
    <div className='App'>
      <Carousel data={mockCarouselData} time={3000}/>
    </div>
  );
}

export default App;