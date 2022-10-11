import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import CarouselPage from './Carousel-Page/Carousel-Page'

ReactDOM.render(
  <React.StrictMode>
    <div className='App'>
    <CarouselPage />
    </div>
    
  </React.StrictMode>,
  document.getElementById('root')
);
