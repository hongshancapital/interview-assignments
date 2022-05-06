import React from "react";
import Carousel from 'components/Carousel';
import "./App.scss";

const items = [
  {
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend. \n Starting at $399.',
    bgImg: 'iphone'
  },
  {
    title: 'Tablet',
    subTitle: 'Just the right amount of everything',
    bgImg: 'tablet'
  },
  {
    title: 'Buy a tablet or xPhone for college. \n Get airPods',
    bgImg: 'airpods'
  }
]
function App() {
  return <div className="App">
    <Carousel className='carousel-banner' items={items} />
  </div>;
}

export default App;
