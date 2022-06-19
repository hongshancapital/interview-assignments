import React from "react";
import "./App.css";
import Carousel, { Slide } from "./components/Carousel";

import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

type AppSlide = Array<{
  imageSrc: string;
  backgroundColor: string;
  title: string;
  text: string;
  color: string;
  testId: string;
}>;

export const slides: AppSlide = [
  {
    imageSrc: iphone,
    backgroundColor: "rgb(17, 17, 17)",
    title: "xPhone",
    text: "Lots to love.Less to spend.\nStarting at $399",
    color: "rgb(250, 250, 250)",
    testId: "xPhone",
  },
  {
    imageSrc: tablet,
    backgroundColor: "rgb(250, 250, 250)",
    title: "Tablet",
    text: "Just the right amount of everything.",
    color: "black",
    testId: "Tablet",
  },
  {
    imageSrc: airpods,
    backgroundColor: "rgb(250, 250, 250)",
    title: "Buy a Tablet or xPhone for college.\nGet arPods.",
    text: "",
    color: "black",
    testId: "arPods",
  },
];

function App() {
  const slideStyle = {
    width: "100%",
    height: "100%",
    backgroundSize: "auto 100%",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
  };

  return (
    <div className="App">
      <Carousel duration={3}>
        {slides.map((s) => {
          return (
            <Slide key={s.title}>
              <div
                data-testid={s.testId}
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
