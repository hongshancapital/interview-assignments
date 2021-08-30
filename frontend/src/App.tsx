import React from "react";
import "./App.css";
import {Carousel} from "./components";

function App() {
  return <div className="App">
    <Carousel indicatorPosition={"top"}>
      <div style={{ backgroundColor: "red", width: '100%', height: '100%'}}/>
      <div style={{ backgroundColor: "black", width: '100%', height: '100%'}}/>
      <div style={{ backgroundColor: "darkviolet", width: '100%', height: '100%'}}/>
      <div style={{ backgroundColor: "dimgray", width: '100%', height: '100%'}}/>
      <div style={{ backgroundColor: "aliceblue", width: '100%', height: '100%'}}/>
      <div style={{ backgroundColor: "salmon", width: '100%', height: '100%'}}/>
    </Carousel>
  </div>;
}

export default App;
