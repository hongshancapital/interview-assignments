import React from "react";
import Carousel from "./components/Carousel";
import img_1 from "./assets/iphone.png";
import img_2 from "./assets/tablet.png";
import img_3 from "./assets/airpods.png";

export const dataList = [
  {
    key: "xPhone",
    url: img_1,
    title: ["xPhone", "Lots to love. Less to spend.Starting at $399"],
  },
  {
    key: "Tablet",
    url: img_2,
    title: ["Tablet", "Just the right amount of everything"],
  },
  {
    key: "airPods",
    url: img_3,
    title: ["Buy a Tablet or xPhone for college. Get airPods"],
  },
];

function App() {
  return (
    <div className="App">
      <Carousel dataList={dataList} delay={3000} />
    </div>
  );
}

export default App;
