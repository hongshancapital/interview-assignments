import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import { carousels } from "./constants/index";

function App(): JSX.Element {
  return (
    <div className="App">
      <Carousel items={carousels} />
    </div>
  );
}

export default App;
