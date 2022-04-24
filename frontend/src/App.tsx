import React, { StrictMode } from "react";

import "./App.css";
import Carousel from "./components/Carousel";

function App() {
  return (
    <StrictMode>
      <div className="App">
        <Carousel />
      </div>
    </StrictMode>
  );
}

export default App;
