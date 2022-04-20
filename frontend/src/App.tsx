import React from "react";
import "./App.css";
import Carousel from "./Carousel"
import tabletImg from './assets/tablet.png'
import iphoneImg from './assets/iphone.png'
import airpodsImg from './assets/airpods.png'

function App() {
  return <div className="App"> 
    <Carousel 
      children={[
        getChild(tabletImg),
        getChild(iphoneImg),
        getChild(airpodsImg)]}
      duration={1000}
      delay={3000}
    />
  </div>;
}

function getChild(url:string){
  return<img src={url} alt="" key={url}></img>
}

export default App;
