import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

function App() {
  return <div className="App">
    <Carousel duration={300} delay={3000} showPagination height={window.innerHeight+'px'}>
        <div className="wrapper phone">
          <div className="title-wrapper">
            <h1 className="title white">
              xPhone
            </h1>
            <p className="text white mrt20">
              Lots to love. Less to spend. <br />
              Starting at $399.
            </p>
          </div>
        </div>
        <div className="wrapper tablet">
          <div className="title-wrapper">
            <h1 className="title black">
              Tablet
            </h1>
            <p className="text black mrt20">
              Just the right amount of everything
            </p>
          </div>
        </div>
        <div className="wrapper airpods">
          <div className="title-wrapper">
            <h1 className="title black">
              Buy a Tablet or xPhone for college. <br />
              Get arPods
            </h1>
          </div>
        </div>
    </Carousel>
  </div>;
}

export default App;
