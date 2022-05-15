import React from "react";
import "./App.css";
import { carouselList } from './constant'
import Carousel from './components/Carousel';
function App() {
  return <div className="App">
    <Carousel carouselList={carouselList}></Carousel>
  </div>;
}

export default App;
