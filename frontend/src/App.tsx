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
        <section className="item item1" key="item1">
          <em className="item-title">xPhone</em>
          <span className="item-content">Lots to love. Less to spend.</span>
          <span className="item-content">Starting at $399</span>
          <img className="item-image" width="100" height="100" src={iphoneUrl} alt="xPhone" />
        </section>

        <section className="item item2" key="item2">
          <em className="item-title">Tablet</em>
          <p className="item-content">Just the right amount of everything</p>
          <img className="item-image" width="100" height="100" src={tabletUrl} alt="Tablet" />
        </section>

        <section className="item item3" key="item3">
          <em className="item-title">Buy a Table or xPhone for college.</em>
          <em className="item-title">Get airPods</em>
          <img className="item-image" width="100" height="100" src={airpodsUrl} alt="airPods" />
        </section>
      </Carousel>
    </div>
  )
}

export default App;
