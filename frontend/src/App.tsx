import React from "react";
import "./App.css";

import Carousel from "./components/Carousel";
import tabletPng from "./assets/tablet.png";
import airpodsPng from "./assets/airpods.png";
import iphonePng from "./assets/iphone.png";

const contentStyle: React.CSSProperties = {
  paddingTop: 50,
  height: "100vh",
  backgroundSize: "100% auto",
  backgroundPositionY: "bottom",
  backgroundRepeat: "no-repeat",
  boxSizing: "border-box",
};

function App() {
  return (
    <div className="App">
      <Carousel initOrder={1} duration={2.5}>
        <div
          style={{
            color: "white",
            backgroundColor: "#111111",
            backgroundImage: `url(${iphonePng})`,
            ...contentStyle,
          }}
        >
          <div className="title">xPhone</div>
          <div className="text">
            Lots to love. Less to spend.
            <br />
            Starting at $399
          </div>
        </div>
        <div
          style={{
            backgroundColor: "#fafafa",
            backgroundImage: `url(${tabletPng})`,
            ...contentStyle,
          }}
        >
          <div className="title">Tablet</div>
          <div className="text">Just the right amount of everything.</div>
        </div>
        <div
          style={{
            backgroundColor: "#f1f1f3",
            backgroundImage: `url(${airpodsPng})`,
            ...contentStyle,
          }}
        >
          <div className="title">
            Buy a Tablet or xPhone for college.
            <br />
            Get arPods.
          </div>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
