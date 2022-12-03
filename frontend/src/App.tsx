import React from 'react';
import './App.css';
import Carousel, {Props as CarouselProps} from './carousel/index';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

const frameData: CarouselProps['frameData'] = [{
  title: 'xPhone',
  describe: <>Lots to love.Less to spend.<br/>Starting at $399.</>,
  url: iphone,
  frameCls: 'xphone-frame'
}, {
  title: 'Tablet',
  describe: 'Just the right amount of everything',
  url: tablet,
  frameCls: 'tablet-frame'
}, {
  title: <>Buy a Tablet or xPhone for college.<br/>Get arPods.</>,
  url: airpods,
  frameCls: 'arpods-frame'
}];

function App() {
  return <div className='App'>
    <Carousel frameData={frameData}/>
  </div>;
}

export default App;
