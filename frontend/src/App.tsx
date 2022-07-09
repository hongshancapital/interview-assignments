import React from "react";
import Carousel from "./components/Carousel";
import "./App.css";
import dataSlides from './data-slides';

function App() {
  return <div className="App"><Carousel slides={dataSlides}/></div>;
}

export default App;
