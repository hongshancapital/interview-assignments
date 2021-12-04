import React from "react";
import Carousel from "./Carousel";
import { SLIDES_DATA, DELAY_TIME } from "../common/constants";
import ProgressBar from "./ProgressBar";
import "../assets/css/App.css";

function App() {
  return (
    <div className="App">
      <Carousel 
        slides={SLIDES_DATA} 
        delay={DELAY_TIME}
        renderNavItem={(navProps) => (
          <ProgressBar style={{width: 50, height: 10, margin: 10}} {...navProps} />
        )} 
        style={{width: '100vw', height: '100vh'}}
      />
    </div>
  );
}

export default App;
