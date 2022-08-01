import React from "react";
import "./App.scss";
import { CarouselDemo } from "./CarouselDemo";

function App() {
  return (
    <div className="App">
      <CarouselDemo autoplay={true}/>
      <CarouselDemo autoplay={false}/>
    </div>
  );
}

export default App;
