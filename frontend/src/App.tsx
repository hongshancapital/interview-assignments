import React from "react";
import Carousel from "./Carousel";
import "./App.css";

function App() {
  return (
    <div className="App">
      <div className="container">
        <Carousel
          options={{
            arrowColor: "#ffffff",
            auto: true,
            dotColor: "#757c75",
            duration: 500,
            interval: 1000,
            loop: true,
            threshold: 100,
          }}
        >
          <div
            className="Frame"
            style={{
              backgroundImage: `url(${require("./assets/iphone.png").default})`,
            }}
          >
            <div className="title">xPhone</div>
            <div className="text">Lots to love. Less to spend</div>
            <div className="text">Starting at $399.</div>
          </div>
          <div
            className="Frame"
            style={{
              backgroundImage: `url(${require("./assets/tablet.png").default})`,
            }}
          >
            <div className="title" style={{ color: "#000000" }}>
              Tablet
            </div>
            <div className="text" style={{ color: "#000000" }}>
              Just the right amount of everything.
            </div>
          </div>
          <div
            className="Frame"
            style={{
              backgroundImage: `url(${
                require("./assets/airpods.png").default
              })`,
            }}
          >
            <div className="title" style={{ color: "#000000" }}>
              Buy a Tablet or xPhone for college
            </div>
            <div className="title" style={{ color: "#000000" }}>
              Get arpods.
            </div>
          </div>
        </Carousel>
      </div>
    </div>
  );
}

export default App;
