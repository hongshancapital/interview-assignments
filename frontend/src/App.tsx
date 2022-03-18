import React from "react";
import "./App.css";
import Carousel from './components/Carousel';
import CarouselData from './data/carousel';

function App() {
  return (
    <div className="App">
      <Carousel data={CarouselData} />
    </div>
  );
}

export default App;
