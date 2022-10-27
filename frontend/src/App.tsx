import React from "react";
import Carousel from './components/Carousel'
import iphoneUrl from './assets/iphone.png'
import tabletUrl from './assets/tablet.png'
import airpodsUrl from './assets/airpods.png'
import "./App.css";

function App() {
  
  return (
    <div className="App">
      <Carousel interval={3000} activeIndex={0}>
        <section className="item item1" style={{ backgroundImage: `url(${iphoneUrl})`}} key="item1">
          <em className="item-title">xPhone</em>
          <span className="item-content">
            Lots to love. Less to spend.<br />
            Starting at $399
          </span>
        </section>

        <section className="item item2" style={{ backgroundImage: `url(${tabletUrl})` }} key="item2">
          <em className="item-title">Tablet</em>
          <span className="item-content">
            Just the right amount of everything
          </span>
        </section>

        <section className="item item3" style={{ backgroundImage: `url(${airpodsUrl})` }} key="item3">
          <em className="item-title">
            Buy a Table or xPhone for college.<br/>
            Get airPods
          </em>
        </section>
      </Carousel>
    </div>
  )
}

export default App;
