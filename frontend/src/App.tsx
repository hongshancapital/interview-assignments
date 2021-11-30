import React from "react";

import Carousel, { RemoteData } from "./components/Carousel";

import "./App.css";

const pageJson: Array<RemoteData> = [
  {
    id: 1,
    title: "xPhone",
    subTitle: "Lots to love.less to spend",
    backgroundColor: "black",
    color: "white",
    imgUrl:
      "https://www.apple.com/v/ipad-mini/n/images/overview/pillars/ideas__e8q89737u9e2_small_2x.jpg",
  },
  {
    id: 2,
    title: "Table",
    backgroundColor: "bisque",
    subTitle: "just the right amount of everything.",
    imgUrl:
      "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/holiday-pg-ipad-202111?wid=600&hei=460&fmt=png-alpha&.v=1635277106000",
  },
  {
    id: 3,
    title: "Buy a Tablet or xPhone for college. Get arPods",
    subTitle: "",
    imgUrl:
      "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/holiday-pg-watchse-202111?wid=600&hei=460&fmt=png-alpha&.v=1635277084000",
  },
  {
    id: 4,
    title: "test test test test ",
    subTitle: "",
    imgUrl:
      "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/holiday-pg-watchse-202111?wid=600&hei=460&fmt=png-alpha&.v=1635277084000",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel list={Carousel.buildPartSample(pageJson)} />
    </div>
  );
}

export default App;
