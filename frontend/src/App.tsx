import React from "react";
import "./App.css";
import { Carousel } from "./components";

function App() {
  return <div className="App">
    <Carousel className="my-carousel-warp">
      <main className="main-wrap w-full h-full iphone color-white">
        <header className="title">
          xPhone
        </header>
        <article className="text">
          Lots to love. Less to spend.<br />
          Starting at $399.
        </article>
      </main>
      <main className="main-wrap w-full h-full tablet">
        <header className="title">
          Tablet
        </header>
        <article className="text">
          Just the right amount of everything.
        </article>
      </main>
      <main className="main-wrap w-full h-full airpods">
        <header className="title">
          Buy a Tablet or xPhone for college.<br />
          Get airPods.
        </header>
      </main>
    </Carousel>
  </div>;
}

export default App;
