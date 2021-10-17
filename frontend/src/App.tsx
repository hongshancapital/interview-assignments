import React from "react";
import "./App.css";
import Carousel from "./components/carousel/Carousel";
import img1 from './assets/airpods.png'
import img2 from './assets/iphone.png'
import img3 from './assets/tablet.png'
console.log(1)
function App() {
  return <div className="App" style={{height:'600px'}}>
    <Carousel>
      <img src={img1} alt="" />
      <img src={img2} alt="" />
      <img src={img3} alt="" />
    </Carousel>  
  </div>;
}

export default App;
