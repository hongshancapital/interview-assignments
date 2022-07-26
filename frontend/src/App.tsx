import React from 'react';
import './App.css';
import { Carousel, ISlideRendererProps, SlideRenderer } from './carousel';

const CAROUSEL_SLIDES: ISlideRendererProps[] = [
  {
    titles: ['title1'],
    descriptions: ['description1'],
    wrapperStyle: {
      backgroundSize: '50%',
      backgroundImage: `url(${require('./assets/iphone.png')})`,
      backgroundColor: '#111',
      color: '#fff'
    }
  },
  {
    titles: ['title2'],
    descriptions: ['description2'],
    wrapperStyle: {
      backgroundSize: '90%',
      backgroundImage: `url(${require('./assets/tablet.png')})`,
      backgroundColor: '#fafafa',
      color: '#000'
    }
  },
  {
    titles: ['title3'],
    descriptions: ['description3'],
    wrapperStyle: {
      backgroundSize: '140%',
      backgroundImage: `url(${require('./assets/airpods.png')})`,
      backgroundColor: '#f1f1f3',
      color: '#000'
    }
  }
];

function App() {
  return (
    <div className='App'>
      <Carousel slideDurationMs={3000}>
        {CAROUSEL_SLIDES.map((slide, index) => <SlideRenderer key={index} {...slide} />)}
      </Carousel>
    </div>
  );
}

export default App;
