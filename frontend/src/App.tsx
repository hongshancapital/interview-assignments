import React from "react";
import "./App.css";
import Carousel from "./Carousel"
import CarouselItem1 from "./Carousel/item1";
import CarouselItem2 from "./Carousel/item2";
import CarouselItem3 from "./Carousel/item3";

function App() {
  return <div className="App"> 
    <Carousel 
      children={[
        <CarouselItem1 key={1}/>,
        <CarouselItem2 key={2}/>,
        <CarouselItem3 key={3}/>]}
      duration={1000}
      delay={3000}
    />
  </div>;
}

function getChild(url:string){
  return<img src={url} alt="" key={url}></img>
}

export default App;
