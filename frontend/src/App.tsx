import React from "react";
import "./App.css";
import Carousel, { Paper } from "./components/Carousel";

function App() {
  return (
    <div className="App">
      <Carousel speed={3000} transitionDuration={1000}>
        <Paper>111</Paper>
        <Paper>222</Paper>
        <Paper>333</Paper>
      </Carousel>
    </div>
  );
}

export default App;
