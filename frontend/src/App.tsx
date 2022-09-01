import React from 'react';
import { Carousel } from './components/index';
import { data } from './mock/data';
import './App.css';

function App() {
  return (
    <div className='App'>
      <Carousel showDots data={data} interval={3000} />
    </div>
  );
}

export default App;
