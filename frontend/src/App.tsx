import React, { useRef } from "react";
import Carousel from "./components/Carousel";
import "./App.css";

type CarouselHandle = React.ElementRef<typeof Carousel>;

function App() {
  const carControl = useRef<CarouselHandle>(null);
  return (
    <div className="App">
      <div className="carousel-wrap">
        <Carousel autoPlay={true} ref={carControl}>
          <div className="item-wapper bg1">
            <div className="text-wrapper">
              <div className="title color-white">xPhone</div>
              <span className="text color-white">
                Lots to love. Less to spend.
              </span>
              <span className="text color-white">Starting at $399.</span>
            </div>
          </div>
          <div className="item-wapper bg2">
            <div className="text-wrapper">
              <div className="title color-black">Tablet</div>
              <span className="text color-black">
                Just the right amount of everything.
              </span>
            </div>
          </div>
          <div className="item-wapper bg3">
            <div className="text-wrapper">
              <div className="title color-black">
                Buy a Tablet or xPhone for college.
              </div>
              <div className="title color-black">Get airPods</div>
            </div>
          </div>
        </Carousel>
      </div>
      <div className="flex-rows gap-8 justify-content-center mt-2">
        <button className="btn" onClick={() => carControl.current?.prev()}>
          上一条
        </button>
        <button className="btn" onClick={() => carControl.current?.next()}>
          下一条
        </button>
        <button className="btn" onClick={() => carControl.current?.go(1)}>
          跳到第2条
        </button>
      </div>
    </div>
  );
}

export default App;
