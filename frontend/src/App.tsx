import React from "react";
import "./App.css";

import Carousel from './components/Carousel';
import { CarouselConfig } from './config';

function App() {
  return (
    <div className="App">
      <Carousel config={CarouselConfig} delay={5000} auto onChange={index => {}} />
    </div>
  );
}

export default App;
