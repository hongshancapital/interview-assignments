import React from "react";
import "./App.css";
import Carousel from './components/carousel';
import Phone from './components/iphone';
import Tablet from './components/tablet';
import Airpods from './components/airpods';

function App() {
  return <div className="App">
    <Carousel autoPlay={true} duration={3000} showIndicator={true}>
      <Phone/>
      <Tablet/>
      <Airpods/>
    </Carousel>
  </div>;
}

export default App;
