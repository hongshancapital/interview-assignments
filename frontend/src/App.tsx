import React from 'react';
import Carousel from './Carousel';
import { slides } from './mock-data';
import './App.css';

function App() {
  return <div className='App'>
    <Carousel slides={slides} />
  </div>;
}

export default App;
