import React from "react";
import "./App.css";
import MyCarousel from "./components/MyCarousel";

function App() {
  return (
    <div className="App">
      <MyCarousel dots={true}>
        <div className="banner">1</div>
        <div className="banner">2</div>
        <div className="banner">3</div>
      </MyCarousel>
    </div>
  );
}

export default App;
