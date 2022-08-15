import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { carouseData } from "./components/Carousel/testData";
import Swiper from "./components/Swiper";

function App() {
  return (
    <div className="App">
      {/* 官方作业 */}
      <Carousel list={carouseData} />
      {/* Swiper核心组件其它实例 */}
      {/* <Swiper
        autoplayTimeout={2000}
        rootStyle={{ width: 300, height: 200, backgroundColor: "black" }}
        indicatorStyle={{
          backgroundColor: "#f00",
          width: 40,
          height: 20,
          borderRadius: 20,
        }}
        activeIndicatorStyle={{
          backgroundColor: "#0f0",
          width: 40,
          height: 20,
          borderRadius: 20,
        }}
      >
        <div style={{ color: "white" }}>123</div>
        <img
          style={{ backgroundColor: "blue", width: "100%", height: "100%" }}
          src={require("./assets/iphone.png")}
        />
      </Swiper> */}
    </div>
  );
}

export default App;
