import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { getSlideDataList } from "./getSlideDataList";
import { getSlideFunctionByData } from "./getSlideFunctionByData";

const slideDataList=getSlideDataList()
const functionList=slideDataList.map(slide => getSlideFunctionByData(slide))

function App() {
  return (
    <div className="App">
      <div
        className="wrapper"
        style={{ height: "100vh", width: "100vw", display: "flex" }}
      >
        <Carousel itemList={functionList}></Carousel>
      </div>
      <a href="#test" className="link"> learn react</a>
    </div>
  );
}

export default App;
