import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { initData } from "./components/stores/data";
function App() {
  const { posters } = initData;
  return <div className="App">
    <Carousel posters={posters} autoPlay={true} time={3000} />
  </div>;
}

export default App;
