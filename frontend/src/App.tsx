import React from 'react';
import './App.css';
import { Carousel } from './components/Carousel';
import { mockData } from './components/Carousel/mock';

function App() {
  return (
    <div className='App'>
      <Carousel dataInfo={mockData} startIndex={1} />
    </div>
  );
}

export default App;
