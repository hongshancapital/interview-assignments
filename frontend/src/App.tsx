import React from "react";
import Carousel from "./components/Carousel";
import CommoditySlice from "./components/CommoditySlice";
import "./App.scss";
import iphonePic from "./assets/iphone.jpg";
import tabletPic from "./assets/tablet.jpg";
import airpodsPic from "./assets/airpods.jpg";

function App() {
  return (
    <div className="App">
      <Carousel pauseOnHover={false} className="commodity-carousel">
        {COMMODITY_SLICE_DATA.map((sliceData, sliceIndex) => {
          return (
            <CommoditySlice
              {...sliceData}
              key={`CommoditySlice${sliceIndex}`}
            />
          );
        })}
      </Carousel>
    </div>
  );
}

// the commodity data
const COMMODITY_SLICE_DATA = [{
  title: "xPhone",
  desc: [
    "Lots to love. Less to spend.",
    "Starting at $399.",
  ],
  pic: iphonePic,
  className: "commodity-xphone",
}, {
  title: "Tablet",
  desc: "Just the right amount of everything.",
  pic: tabletPic,
  className: "commodity-tablet",
}, {
  title: [
    "Buy a Tablet or xPhone for college.",
    "Get arPods.",
  ],
  pic: airpodsPic,
  className: "commodity-arpods",
}];

export default App;
