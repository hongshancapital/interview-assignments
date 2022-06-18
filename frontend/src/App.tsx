import React from "react";
import "./App.css";
import Carousel, { Slide } from "./components/Carousel";

import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

function App() {
  const slideStyle = {
    width: "100%",
    height: "100%",
    backgroundSize: "auto 100%",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
  };

  const slides = [
    {
      imageSrc: iphone,
      backgroundColor: "#111111",
      title: "xPhone",
      text: "Lots to love.Less to spend.\nStarting at $399",
      color: "#fafafa",
    },
    {
      imageSrc: tablet,
      backgroundColor: "#fafafa",
      title: "Tablet",
      text: "Just the right amount of everything.",
      color: "black",
    },
    {
      imageSrc: airpods,
      backgroundColor: "#fafafa",
      title: "Buy a Tablet or xPhone for college.\nGet arPods.",
      color: "black",
    },
  ];

  return (
    <div className="App">
      <Carousel duration={3}>
        {slides.map((s) => {
          return (
            <Slide>
              <div
                style={{
                  background: `no-repeat url(${s.imageSrc})`,
                  backgroundColor: s.backgroundColor,
                  color: s.color,
                  ...slideStyle,
                }}
              >
                <div className="desc">
                  <p className="title">{s.title}</p>
                  {s.text && <p className="text">{s.text}</p>}
                </div>
              </div>
            </Slide>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
