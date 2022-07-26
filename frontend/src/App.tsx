import React from "react";
import "./App.css";
import { CarouselDemo } from "./components/carousel-demo";
import { carouselList } from "./mock/data";

function App() {
  return <div className="App">
    <CarouselDemo items={carouselList} />
  </div>;
}

export default App;
