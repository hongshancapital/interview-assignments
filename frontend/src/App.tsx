import React from "react";
import "./App.css";

import { Carousel } from "./Carousel/index";
import { LIST } from './Carousel/const';

function App() {
  return (
    <div className="App">
      <Carousel list={LIST} duration={2000} />
    </div>
  );
}

export default App;
