import React from "react";
import { Carousel } from "./components/Carousel"; 
import "./App.css";

import aripodsImage from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

function App() {
  return (
    <div className="App">
      <Carousel images={[aripodsImage, iphone, tablet]} />
    </div>
  );
}

export default App;
