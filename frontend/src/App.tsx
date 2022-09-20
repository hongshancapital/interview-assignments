import React from "react";
import Carousel from "./components/Carousel";
import { mockData } from "./mock/carousel";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel data={mockData} />
    </div>
  );
}

export default App;
