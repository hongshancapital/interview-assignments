import React from "react";

import Carousel from "./components/Carousel";
import getSlideList from "./utils/getSlideList";

import "./App.css";

const slideList = getSlideList();

function App() {
  return (
    <div className="App">
      <Carousel slideList={slideList} />
    </div>
  );
}

export default App;
