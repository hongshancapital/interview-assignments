import React from "react";
import Carousel from "./Carousel";
import { SLIDES_DATA, DELAY_TIME } from "../common/constants";
import "../assets/css/App.css";

function App() {
  return (
  	<div className="App">
  		<Carousel slides={SLIDES_DATA} delay={DELAY_TIME} />
  	</div>
  );
}

export default App;
