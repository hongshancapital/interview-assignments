import React from "react";
import "./App.css";
import Carousel from "./component/Carousel";
import airpodsIcon from "./assets/airpods.png";
import iphoneIcon from "./assets/iphone.png";
import tabletIcon from "./assets/tablet.png";

function App() {
  return (
    <div className="App">
      <Carousel>
        <img src={airpodsIcon} alt="" width="100%"/>
        <img src={iphoneIcon} key="iphone" alt=""  width="100%"/>
        <img src={tabletIcon} key="tableIcon" alt=""  width="100%"/>
      </Carousel>
    </div>
  );
}

export default App;
