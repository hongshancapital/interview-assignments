import React from "react";
import "./App.css";
import Carousel from "./components/carousel";

import pic0 from './assets/iphone.png';
import pic1 from './assets/tablet.png';
import pic2 from './assets/airpods.png';

const items = [
  {
    bgCover: pic0,
    content: (
      <div style={ { color: 'white' } }>
        <p className="title">xPhone</p>
        <p className="text">Lots to love. Less to spend.<br/>Starting at $399.</p>
      </div>
    ),
  },
  {
    bgCover: pic1,
    content: (
      <div>
        <p className="title">Tablet</p>
        <p className="text">Just the right amount of everything</p>
      </div>
    )
  },
  {
    bgCover: pic2,
    content: (
      <div>
        <p className="title">Buy a Tablet or xPhone for college.</p>
        <p className="title">Get arPods.</p>
      </div>
    )
  }
]

function App() {
  return <div className="App">
    <Carousel items={items} duration={3000} />
  </div>;
}

export default App;
