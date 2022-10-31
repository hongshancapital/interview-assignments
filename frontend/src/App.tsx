import React from "react";
import "./App.css";
import Carousel from './Carousel';

function App() {
  return <div className="App">
    <Carousel interval={3000}>
      <section className="page page-1">
        <h2 className="title">xPhone</h2>
        <p className="text">
            Lots to love. Less to spend.<br />
            Starting at $399
        </p>
      </section>
      <section className="page page-2">
        <h2 className="title">Tablet</h2>
        <p className="text">
          Just the right amount of everything
        </p>
      </section>
      <section className="page page-3">
        <h2 className="title">Buy a Table or xPhone for college.<br/> Get airPods</h2>
      </section>
    </Carousel>
  </div>;
}

export default App;
