import React from 'react';
import { Carousel } from './components';
import { AirpodsIcon, IphoneIcon, TabletIcon } from './assets';
import './App.css';

const renderCarouselItem = (image: string, dataType: string) => {
 return (
  <>
   <img src={image} alt='' className='preview' data-type={dataType} />
  </>
 );
};

function App() {
 const options = [
  {
    key: 'step1',
   content: renderCarouselItem(AirpodsIcon, 'airpods'),
  },
  {
    key: 'step2',
   content: renderCarouselItem(IphoneIcon, 'iphone'),
  },
  {
    key: 'step3',
   content: renderCarouselItem(TabletIcon, 'tablet'),
  },
 ];
 return (
  <div className='App'>
   <Carousel options={options} />
  </div>
 );
}

export default App;
