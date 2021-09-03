import React from "react";
import Carousel from "./Carousel";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel list={[1, 2, 3]} />
    </div>
  );
}

export default App;
