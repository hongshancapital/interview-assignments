import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

function App() {
  const onCarouselClick = (url:string,index: Number)=>{
    // todo
  };
  const contentList = [
    {
      url: require("./assets/iphone.png"),
      title: "xPhone",
      subTitle: "Lots to love. Less to spend. Starting at $399",
      contentStyle: {color: "#FFF",width: "400px"}
    },
    {
      url: require("./assets/tablet.png"),
      title: "Tablet",
      subTitle: "Just the right amount of everything.",
    },
    {
      url: require("./assets/airpods.png"),
      title: "Buy a Tablet or xPhone for college Get arPods.",
      contentStyle: {width: "800px"}
    },
  ]
  return <div className="App">
    <Carousel contentList={contentList} delay={2000} carouselClick={onCarouselClick}></Carousel>
  </div>;
}

export default App;
