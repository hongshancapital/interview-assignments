import React, { useEffect, useState } from "react";
import "./App.css";
import Carousel from "./views/carousel";

const sliders = [
  require("./assets/airpods.png"),
  require("./assets/iphone.png"),
  require("./assets/tablet.png")
]

const prefix = "eve";

function App() {

  return <div className="App">
    {/* write your component here */}
    <Carousel 
      autoPlay={3000}
      style={{ height: "100vh" }}
      selectedIndex={0}
      showIndicators={false}
      indicator={null}
      dots={null}
    >
      {sliders.map((slider, index) => (
          <div 
            key={`swiper-slider_${index}`}
            className={`${prefix}-slider`}
            style={{
              width: "100vw",
              height: "100vh",
              background: `url(${slider}) no-repeat center/contain`
            }}
          ></div>
        ))}
    </Carousel>
  </div>;
}

export default App;
