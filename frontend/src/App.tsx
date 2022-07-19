import React from "react";
import "./App.css";
import Carsoule from './components/Carsousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

const imgList = [
  {key:1 , url: airpods},
  {key:2 , url: iphone},
  {key:3 , url: tablet},
]

function App() {
  return <div className="App">
    <Carsoule imgList={imgList}/>
  </div>;
}

export default App;
