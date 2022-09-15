import React from "react";
import Carousel from "./components/Carousel";
import "./App.css";
import airpods from "./assets/airpods.png";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";

function App() {
  return (
    <div className="App">
      <Carousel
        timeout={3000}
        datasource={[
          {
            title: "Buy a Tablet or xPhone for college.<br/>Get arPods.",
            sub: "",
            image: airpods,
            color: "#000",
          },
          {
            title: "xPhone",
            sub: "Lots to love. Less to spend.<br/>Starting at $399.",
            image: iphone,
            color: "#fff",
          },
          {
            title: "Tablet",
            sub: "Just the right amount of everything.",
            image: tablet,
            color: "#000",
          },
        ]}
      />
    </div>
  );
}

export default App;
