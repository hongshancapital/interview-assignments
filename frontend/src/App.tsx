import React from "react";
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel
        items={[
          {
            title: "xPhone",
            subTitle: ["Lots to love. Less to spend.", "Starting at $399."],
            image: iphone,
            backColor: "#111111",
            foreColor: "white",
          },
          {
            title: "Tablet",
            subTitle: "Just the right amount of everything.",
            image: tablet,
            backColor: "#fafafa",
            foreColor: "black",
          },
          {
            title: ["Buy a Tablet or xPhone for college.", "Get arPods"],
            image: airpods,
            backColor: "#f2f2f3",
            foreColor: "black",
          },
        ]}
      />
    </div>
  );
}

export default App;
