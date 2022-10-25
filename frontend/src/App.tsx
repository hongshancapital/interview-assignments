import React from "react";
import Carousel from "./components/Carousel";
import demoConfig from "./components/Carousel/demoConfig";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel {...demoConfig} />
    </div>
  );
}

export default App;
