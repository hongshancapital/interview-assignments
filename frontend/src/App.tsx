import React from 'react';
import './App.scss';
import Carousel from './components/Carousel';
import { CAROUSEL_LIST } from './constants';

function App() {
  return (
    <div className="App">
      <Carousel list={CAROUSEL_LIST} />
    </div>
  );
}

export default App;
