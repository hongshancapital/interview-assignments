import React from "react";
import { Carousel } from "./components/Carousel";

import iphoneImage from "./assets/iphone.png";
import tabletImage from "./assets/tablet.png";
import airpodsImage from "./assets/airpods.png";

import "./App.css";

const { Slide } = Carousel;

function App(): JSX.Element {
  return <div className="App">
    <Carousel duration={3000}>
      <Slide
          title="xPhone"
          src={iphoneImage}
          className="xPhoneSlide"
          content={"Lots to love. Less to spend. \n Starting at $399."}
      />
      <Slide
          title="Tablet"
          src={tabletImage}
          content={"Just the right amount of everything."}
          className="tabletSlide"
      />
      <Slide
          title={"Buy a Tablet or xPhone for College.\n Get arPods."}
          src={airpodsImage}
          className="airpodsSlide"
      />
    </Carousel>
  </div>;
}

export default App;
