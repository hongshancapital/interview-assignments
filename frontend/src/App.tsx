import React, { useMemo } from "react";
import "./App.css";
import Carousel from "./carousel";
import Banner, { BannerConfig } from "./banner";
function App() {
  const items = useMemo(() => {
    let items: BannerConfig[] = [
      {
        key: "iphone",
        img: require("./assets/iphone.png"),
        title: "xPhone",
        des: "Lots to love. Less to spend.\n Starting at $399",
        color: "white",
      },
      {
        key: "tablet",
        img: require("./assets/tablet.png"),
        title: "Tablet",
        des: "Just the right amount of everything.",
      },
      {
        key: "airpods",
        img: require("./assets/airpods.png"),
        title: "Buy a Tablet or xPhone for college.\nGet airPods.",
      },
      {
        key: "iphone1",
        img: require("./assets/iphone.png"),
        title: "xPhone",
        des: "Lots to love. Less to spend.\n Starting at $399",
        color: "white",
      },
      {
        key: "tablet1",
        img: require("./assets/tablet.png"),
        title: "Tablet",
        des: "Just the right amount of everything.",
      },
      {
        key: "airpods1",
        img: require("./assets/airpods.png"),
        title: "Buy a Tablet or xPhone for college.\nGet airPods.",
      },
    ];
    return items.map((item) => {
      return <Banner {...item}></Banner>;
    });
  }, []);

  return (
    <div className="App">
      <Carousel
        items={items}
        style={{ width: "100%", height: "100vh" }}
      ></Carousel>
    </div>
  );
}

export default App;
