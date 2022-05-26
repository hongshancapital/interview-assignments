import React from "react";
import { Carousel } from './components';
import "./App.css";

function App() {
  return <div className="App">
    <Carousel>
      <div className="demo-item-red">a</div>
      <div className="demo-item-blue">b</div>
      <div className="demo-item-yellow">c</div>
      <div className="demo-item-green">d</div>
    </Carousel>
  </div>;
}

export default App;
