import React from "react";
import Carousel from "./components/carousel";
import Slide from "./components/carousel/slide";
import { ISlideProps } from './components/carousel/type';
import "./App.css";
import iphonePNG from "./assets/iphone.png";
import tabletPNG from "./assets/tablet.png";
import airpodsPNG from "./assets/airpods.png";

const slideList: ISlideProps[] = [
  {
    title: ["xPhone"],
    description: ["Lots to love. Less to spend.", "Starting at $399"],
    img: iphonePNG,
    backgroundColor: "#111",
    color: "#fff"
  },
  {
    title: ["Tablet"],
    description: ["Just the right amount of everything"],
    img: tabletPNG,
    backgroundColor: "#fafafa",
  },
  {
    title: ["Buy a Tablet or xPhone for college.", "Get airPods"],
    img: airpodsPNG,
    backgroundColor: "#f1f1f3",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel autoPlay style={{
        height: '100vh',
      }}>
        {slideList.map((item: ISlideProps, index: number) => (
          <Slide key={index} {...item} />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
