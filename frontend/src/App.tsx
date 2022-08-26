import React from "react";
import "./App.css";
import Carousel from './components/carousel'
function App() {
  return <div className="App">
    <Carousel interval={3000}>
      <div className="iphone">
        <h1 className="title">xPhone</h1>
        <p className="info">Lots to love.Less to spend.</p>
        <p className="info">Starting at $399.</p>
      </div>
      <div className="tablet">
        <h1 className="title">Tablet</h1>
        <p className="info">Just the right amount of everythins.</p>
      </div>
      <div className="airpods">
        <h1 className="title">Buy a Tablet or xPhone for college.</h1>
        <h1 className="title second-row">Get arPods.</h1>
      </div>
    </Carousel>
  </div>;
}

export default App;
