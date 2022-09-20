import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import CarouselItem from "./components/carousel/item";
import Demo from "./components/demo";

function App() {
  const data: {
    title?: string;
    desc?: string;
    className?: string;
  }[] = [{
    title: 'xPhone',
    desc: 'Lots to love.Less to Spend. Starting at $399.',
    className: 'x-phone'
  }, {
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    className: 'tablet'
  }, {
    title: 'Buy a Tablet or xPhone for college.\nGet arPods',
    className: 'airpods'
  }]
  return <div className="App">
    <div className="title">carousel demo</div>
    <Carousel className="demo">
      {data.map((item, i) => {
        return <CarouselItem key={i}>
          <Demo title={item.title} desc={item.desc} className={item.className}/>
        </CarouselItem>
      })}
    </Carousel>
  </div>;
}

export default App;
