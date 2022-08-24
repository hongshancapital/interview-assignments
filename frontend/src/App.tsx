import React from "react";
import "./App.css";

import { Carousel } from './components/Carousel'
import { CarouselItem } from './components/CarouselItem'

// const arr = [1, 2, 3]
const renderItem1 = () => (
  <div className="carousel-item-1">
  <div className="item-title">xPhone</div>
  <div className="item-desc">Lots to love. Less to spand.</div>
  <div className="item-desc">Starting at $399.</div>
</div>
)
const renderItem2 = () => (
  <div className="carousel-item-2">
  <div className="item-title">Tablet</div>
  <div className="item-desc">Just the right amount of everything.</div>
</div>
)
const renderItem3 = () => (
  <div className="carousel-item-3">
  <div className="item-title">Buy a tablet or xPhone for college.</div>
  <div className="item-desc">Get arPods.</div>
</div>
)
const itemArr = [
  renderItem1,
  renderItem2,
  renderItem3
]

const items = itemArr.map((item, index) => (
  <CarouselItem
    key={index}
  >
    {item()}
  </CarouselItem>
))

function App() {
  return <div className="App">
    <div className="my-carousel-test">
      <Carousel>
        {items}
      </Carousel>
    </div>
  </div>;
}

export default App;
