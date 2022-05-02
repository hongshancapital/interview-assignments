import React from "react";
import "./App.css";
import Carousel from './components/Carousel';

export const carouselItems = [
  {
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\n Starting at $399.',
    className: 'iphone',
  },
  {
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
    className: 'tablet',
  },
  {
    title: 'Buy a tablet or xPhone for college.\n Get airPods',
    subTitle: '',
    className: 'airpods',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel items={carouselItems} autoPlay startIndex={2} duration={3000} speed={300} />
    </div>
  );
}

export default App;
