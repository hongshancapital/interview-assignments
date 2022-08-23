import React, {HTMLAttributes} from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

const getBgStyle = (url: string): HTMLAttributes<HTMLDivElement> => {
  return {
    style: {
      backgroundImage: `url('${url}')`
    }
  }
}

function App() {
  return <div className="App">
    <Carousel>
      <div className="carousel-item carousel-item--iphone" {...getBgStyle(iphone)}>
        <h2 className="title">xPhone</h2>
        <p className="text">Lots to love.Less to spend.</p>
        <p className="text">Starting at $399.</p>
      </div>
      <div className="carousel-item" {...getBgStyle(tablet)}>
        <h2 className="title">Tablet</h2>
        <p className="text">Just the right amount of everything.</p>
      </div>
      <div className="carousel-item" {...getBgStyle(airpods)}>
        <h2 className="title">
          <div>Buy a Tablet or xPhone for college.</div>
          <div>Get arPods</div>
        </h2>
      </div>
    </Carousel>
  </div>;
}

export default App;
