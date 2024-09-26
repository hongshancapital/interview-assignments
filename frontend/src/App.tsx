import React from 'react';
import Carousel from './carousel';
import { CarouselData } from './const';

const beforeChange = (from: number, to: number) => {
  console.log('beforeChange', `${from}-${to}`);
};

const afterChange = (current: number) => {
  console.log('afterChange', current);
};

function App() {
  return (
    <Carousel
      duration={3000}
      beforeChange={beforeChange}
      afterChange={afterChange}
      data={CarouselData}
    />
  );
}

export default App;
