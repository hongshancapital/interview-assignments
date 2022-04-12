import React from 'react';
import './App.scss';
import Carousel from './components/carousel';
import phoneImage from './assets/iphone.png';
import tabletImage from './assets/tablet.png';
import airpodsImage from './assets/airpods.png';
import Page, { IProps } from './components/page';

const data:IProps[] = [{
  image: phoneImage,
  title: 'xPhone',
  descriptions: ['Lots to love. Less to spend.', 'Starting at $399.'],
  className: 'phone-container'
}, {
  image: tabletImage,
  title: 'Tablet',
  descriptions: ['Just the right amount of everything.'],
  className: 'tablet-container'
}, {
  image: airpodsImage,
  title: 'Buy a Tablet or xPhone for college.',
  descriptions: ['Get arPods.'],
  className: 'airpods-container'
}]

function App() {
  return <div className='App'>
    <Carousel autoPlay={true} duration={3000} showIndicator={true}>
      {data.map(page => 
        <Page key={page.className} {...page}/>
      )}
    </Carousel>
  </div>;
}

export default App;
