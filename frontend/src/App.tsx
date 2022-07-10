import React from "react";
import "./App.css";
import { Carousel } from "./components/carousel";

function App() {

  return <div className="App">
    <Carousel content={[
      <div className="x-phone part-one">
        <div className="x-phone-header">x-Phone</div>
        <div className="x-phone-subtitle">Lots to love.less to spend</div>
        <div className="x-phone-subtitle">Starting at $399</div>
      </div>,
      <div className="tablet">
        <div className="tablet-header">Tablet</div>
        <div className="tablet-header">Just the right amount of everything</div>
      </div>,
      <div className="airpods">
        <div className="airpods-header">Buy a Tablet or XPhone for college</div>
        <div className="airpods-header">Get arPods</div>
      </div>
    ]}></Carousel>
  </div>;
}

export default App;
