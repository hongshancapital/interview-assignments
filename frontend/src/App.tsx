import React from "react";
import "./App.css";
import Carousel from './Carousel/Carousel';

function App() {
  return <div className="App">
    <Carousel initialIndex={1}>
      <div>1</div>
      <div>2</div>
      <div>3</div>
    </Carousel>
  </div>;
}

export default App;
