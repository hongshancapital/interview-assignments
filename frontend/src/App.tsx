import React from 'react';
import './App.css';
import { Carousel, ISlideRendererProps, SlideRenderer } from './carousel';

const CAROUSEL_SLIDES: ISlideRendererProps[] = [
  {
    titles: ["xPhone"],
    descriptions: ["Lots to love. Less to spend.", "Starting at $399."],
    wrapperStyle: {
      backgroundSize: '50%',
      backgroundImage: `url(${require('./assets/iphone.png')})`,
      backgroundColor: '#111',
      color: '#fff'
    }
  },
  {
    titles: ['Tablet'],
    descriptions: ['Just the right amount of everything.'],
    wrapperStyle: {
      backgroundSize: '90%',
      backgroundImage: `url(${require('./assets/tablet.png')})`,
      backgroundColor: '#fafafa',
      color: '#000'
    }
  },
  {
    titles: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    descriptions: [],
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
