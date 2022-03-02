import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { AirpodsImg, IphoneImg, TabletImg } from "./components/Demo";

function App() {
  return (
    <div className="App">
      <Carousel cycleTime={3000}>
        <IphoneImg />
        <AirpodsImg />
        <TabletImg />
      </Carousel>
    </div>
  );
}

export default App;
