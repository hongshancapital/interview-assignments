import React from "react";
import Carousel from '../src/pages/carousel/index'
import airPods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import "./App.css";

const child = (
  <div className="children">
    <div className="iphone">
      <div className="iphoneBox">
        <p>xPhone</p>
        <div className="iphoneContent">
          Lots to love.Less to spend.
          Stating at $399
        </div>
      </div>
      <img src={iphone}/>
    </div>
    <div className="tablet">
    <div className="tabletBox">
        <p>Tablet</p>
        <div className="tabletContent">
          Just the right amonunt of everything
        </div>
      </div>
      <img src={tablet} />
    </div>
    <div className="airPods">
    <div className="airPodsBox">
        <p>Buy a tablet or xPhone for college. Get airPods</p>
        <div></div>
      </div>
      <img src={airPods} />
    </div>
  </div>
)

function App() {
  return <div className="App"><Carousel children={child}/></div>;
}

export default App;
