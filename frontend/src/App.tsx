import React from "react";
import "./App.css";
import Swiper from "./components/Swiper";



function App() {
  return <div className="App">
    <Swiper perStayTime={4000} perAniTime={300} initcialIndex={0} autoPlay={true}>
      <div className="swiper__item">
        <div className="swiper__item__one">
          <h1 className="swiper__item_title">xPhone</h1>
          <h4>Lots to love.Less to spend. Starting at $399.</h4>
        </div>
      </div>
      <div className="swiper__item">
        <div className="swiper__item__two">
          <h1 className="swiper__item_title">Tablet</h1>
          <h4>Just the right amount of everything.</h4>
        </div>
      </div>
      <div className="swiper__item">
        <div className="swiper__item__three">
          <h1 className="swiper__item_title">
            Buy a Tablet or xPhone for college. Get arPods.
          </h1>
        </div>
      </div>
    </Swiper>
  </div>;
}

export default App;
