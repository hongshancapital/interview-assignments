import React from "react";
import "./App.css";
import { Carousel } from "./components/Carousel";
import { CarouselItem } from "./components/CarouselItem";
import { ICarouselItemProps } from "./components/CarouselItem/interface";

// 引入图片资源
import XPhoneImage from "./assets/iphone.png"
import TabletImage from "./assets/tablet.png"
import ARPodsImage from "./assets/airpods.png"

const carouselList: ICarouselItemProps [] = [{
  title: "xPhone",
  description: "Lots to Love. Less to speed.\nStarting at $399",
  background: "#111",
  color: "white",
  image: XPhoneImage
}, {
  title: "Tablet",
  description: "Just the right amount of everything.",
  background: "#FAFAFA",
  color: "black",
  image: TabletImage
}, {
  title: "Bug a Tablet or xPhone for college.\nGet arPods",
  description: "",
  background: "#F1F1F3",
  color: "black",
  image: ARPodsImage
}]

function App() {
  return <div className="App">{
    <Carousel>
      {
        carouselList.map((item, index) => (
          <CarouselItem key={index} title={item.title} description={item.description} background={item.background} color={item.color} image={item.image}/>
        ))
      }
    </Carousel>
  }</div>;
}

export default App;
