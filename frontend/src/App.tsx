import React from 'react';
import Carousel from './components/Carousel';
import { DEFAULT_ITEMS } from './constants/config';

import './App.css';

function App() {
  return (
    <div className='App'>
      <Carousel
        items={DEFAULT_ITEMS}
        duration={3000}
        speed={400}
      />
    </div>
  );
}

export default App;
