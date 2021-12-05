import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { getBannerList } from "./getBannerList";

function App() {
  return (
    <div className="App">
      <div
        className="wrapper"
        style={{ height: "900px", width: "100%", display: "flex" }}
      >
        <Carousel itemList={getBannerList()}></Carousel>
      </div>
      <a href="#test"> learn react</a>
    </div>
  );
}

export default App;
