import React from "react";
import "./App.css";
import Carousel from './component/Carousel';
import IphoneImg from "./assets/iphone.png";
import TabletImg from "./assets/tablet.png";
import AirpodsImg from "./assets/airpods.png";

function App() {
  const list = [
    {
      key: '1',
      title: "xPhone",
      text: ["Lots to love. Less to spend.", "Starting at $399."],
      panelStyle: { backgroundColor: "#000", color: "#fff" },
      goodsImgStyle: {
        width: "322px",
        height: "400px",
        borderRadius: "38px",
        background: `url(${IphoneImg}) -1279px -1208px no-repeat`,
      },
    },
    {
      key: '2',
      title: "Tablet",
      text: "Just the right amount of everything.",
      panelStyle: {
        backgroundColor: "#FAFAFA",
      },
      goodsImgStyle: {
        width: "404px",
        height: "400px",
        borderRadius: "38px",
        background: `url(${TabletImg}) -2679px -1208px no-repeat`,
      },
    },
    {
      key: '3',
      title: ["Bug a Tablet or xPhone for college.", "Get airPods."],
      panelStyle: {
        backgroundColor: "#f1f1f3",
      },
      goodsImgStyle: {
        width: "402px",
        height: "410px",
        borderRadius: "50px",
        background: `url(${AirpodsImg}) -4119px -1184px no-repeat`,
      },
    },
  ];
  return <div className="App">
    <Carousel list={list} />
  </div>;
}

export default App;
