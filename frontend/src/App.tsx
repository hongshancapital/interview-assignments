import React from 'react';
import './App.css';
import { Carousel, ISlideRendererProps, SlideRenderer } from './carousel';

const CAROUSEL_SLIDES: ISlideRendererProps[] = [
  {
    titles: ['title1'],
    descriptions: ['description1'],
    image: {
      src: require('./assets/iphone.png'),
      alt: 'iPhone'
    }
  },
  {
    titles: ['title2'],
    descriptions: ['description2'],
    image: {
      src: require('./assets/tablet.png'),
      alt: 'tablet'
    }
  },
  {
    titles: ['title3'],
    descriptions: ['description3'],
    image: {
      src: require('./assets/airpods.png'),
      alt: 'airpods'
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
