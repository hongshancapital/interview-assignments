import React from "react";
import "./App.css";

import Iphone from './assets/iphone.png'

import Carousel from './components/Carousel'

function App() {
  return <div className="App">
    <Carousel autoplay>
      <div className="box no1">
        <div className="title title-padding">xPhone</div>
        <div className="text">Lots to Love. Less to spend.</div>
        <div className="text">Starting at $399</div>
      </div>
      <div className="box no2">
        <div className="title black title-padding">Tablet</div>
        <div className="text black">Just the right amount of everything.</div>
      </div>
      <div className="box no3">
        <div className="title black">Buy a Tablet or xPhone for college.</div>
        <div className="title black">Get arPods.</div>
      </div>
    </Carousel>
  </div>;
}

export default App;
