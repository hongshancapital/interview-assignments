import './App.css';

import React from 'react';

import ImgAirpods from './assets/airpods.png';
import ImgIphone from './assets/iphone.png';
import ImgTablet from './assets/tablet.png';
import { Carousel } from './components';

const carouselSettings: {
  title: string[];
  desc: string[];
  img: string;
  backgroundColor: string;
  color: string;
}[] = [
  {
    title: ["xPhone"],
    desc: ["Lots to love. Less to spend.", "Starting at $399"],
    img: ImgIphone,
    backgroundColor: "#111111",
    color: "white",
  },
  {
    title: ["Tablet"],
    desc: ["Just the right amount of everything."],
    img: ImgTablet,
    backgroundColor: "#fafafa",
    color: "black",
  },
  {
    title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
    desc: [],
    img: ImgAirpods,
    backgroundColor: "#f1f1f3",
    color: "black",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel>
        {carouselSettings.map(
          ({ title, desc, img, backgroundColor, color }) => {
            return (
              <div
                key={img}
                style={{
                  width: "100%",
                  height: "100vh",
                  overflow: "hidden",
                  position: "relative",
                  paddingTop: "120px",
                  boxSizing: "border-box",
                  backgroundColor,
                  color,
                }}
              >
                {title.map((text) => {
                  return <h1 key={text}>{text}</h1>;
                })}
                {desc.map((text) => {
                  return <h3 key={text}>{text}</h3>;
                })}
                <img
                  style={{
                    position: "absolute",
                    left: "50%",
                    bottom: "160px",
                    transform: "translateX(-50%)",
                    width: 200,
                    height: 200,
                  }}
                  src={img}
                  alt="goods"
                />
              </div>
            );
          }
        )}
      </Carousel>
    </div>
  );
}

export default App;
