import React from "react";
import Carousel from "./components/Carousel";
import { SLIDES } from "./const";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel items={SLIDES} />
    </div>
  );
}

export default App;
