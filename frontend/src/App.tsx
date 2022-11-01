import React from "react";
import Carousel from "./components/Carousel";
import defaultConfig from "./components/Carousel/defaultConfig";
import "./App.scss";

function App() {
  return (
    <div className="App">
      <Carousel {...defaultConfig} />
    </div>
  );
}

export default App;
