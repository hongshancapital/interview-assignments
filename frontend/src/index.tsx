import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

// 数据化 -- 根据numer动态固定位置，而不是用下标，若修改图片只需更改url即可.
const renderPicObj = [
  {
    number: 1,
    url: iphone,
    title: "xPhone",
    desc: "Lots to love. Less to spend. starting at $399.",
    color: "#FFFFFF",
    bgColor: "#101010",
  },
  {
    number: 2,
    url: tablet,
    title: "Tablet",
    desc: "Just the right amount of everything.",
    color: "#101010",
    bgColor: "#FAFAFA",
  },
  {
    number: 3,
    url: airpods,
    title: "Buy a Tablet Or xPhone for college. Get arPods.",
    color: "#101010",
    bgColor: "#F2F2F3",
  },
];

ReactDOM.render(
  <React.StrictMode>
    <App renderPicObj={renderPicObj} />
  </React.StrictMode>,
  document.getElementById("root")
);
