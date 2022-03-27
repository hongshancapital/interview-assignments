import React from "react";
import "./App.css";
import { Carousel, ICarouselProps } from './components/Carousel';

const items: ICarouselProps['items'] = [
  {
    classname: 'iphone',
    title: 'xPhone',
    subtitle: <>
      <div>Lots to love.Less to spend.</div>
      <div>Starting at $399.</div>
    </>,
    titleColor: '#fff',
  },
  {
    classname: 'tablet',
    title: 'Tablet',
    subtitle: 'Just the right amount of everything.',
  },
  {
    classname: 'airpods',
    title: <>
      <div>Buy a Tablet or xPhone for college.</div>
      <div>Get Airpods.</div>
    </>,
  },
]

function App() {
  return <div className="App">
    <Carousel items={items} durationMS={3000} style={{ height: '56.25vw' }} />
  </div>;
}

export default App;
