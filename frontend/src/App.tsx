import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import airpods from "./assets/airpods.png"
import iphone from "./assets/iphone.png"
import tablet from "./assets/tablet.png"
const imgList = [airpods,iphone,tablet]
function App() {
  return <div className="App">
    <Carousel imgList={imgList}/>
  </div>;
}

export default App;
