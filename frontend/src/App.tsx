import React from "react";
import "./App.css";
import Carousel from "./components/carousel/Carousel";
import img3 from './assets/airpods.png'
import img1 from './assets/iphone.png'
import img2 from './assets/tablet.png'
function App() {
  return <div className="App" style={{ height: '100%' }}>
    <Carousel>
    <div style={{ backgroundColor: '#111111', color: '#fff' }}>
        <p className="pt200 font36">xPhone</p>
        <p className="pt30 font18">lots to love.Less to spend.</p>
        <p className="font18">Starting at $399.</p>
        <img src={img1} alt="" width="50%" />
      </div>
      <div style={{ backgroundColor: '#fafafa', color: '#111111' }}>
        <p className="pt200 font36">Tablet</p>
        <p className="pt30 font18">Just the right amount of everything.</p>
        <img src={img2} alt="" width="100%" />
      </div>
      <div style={{ backgroundColor: '#f1f1f3', color: '#111111' }}>
        <p className="pt200 font36">Buy a Tablet or xPhone for college</p>
        <p className="font36">Get arPods</p>
        <img src={img3} alt="" width="100%" />
      </div>
    </Carousel>
  </div>;
}

export default App;
