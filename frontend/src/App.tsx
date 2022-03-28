import React from "react";
import Carousel from "./components/Carousel";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel className="h-screen">
        <div className="content-item item-one">
          <h2 className="title">xPhone</h2>
          <div className="text">Lots to love. Less to spend.</div>
          <div className="text">Sarting at $399.</div>
        </div>
        <div className="content-item item-two">
          <h2 className="title">Tablet</h2>
          <div className="text">Just th right amount of everything.</div>
        </div>
        <div className="content-item item-three">
          <h2 className="title">Buy a Tablet or xPhone for college.</h2>
          <h2 className="title">Get arPods.</h2>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
