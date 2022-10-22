import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import { carouselList } from './mock'

function App() {
  return <div className="App">
    <Carousel sourceList={carouselList} duration={3000} />
  </div>;
}

export default App;
