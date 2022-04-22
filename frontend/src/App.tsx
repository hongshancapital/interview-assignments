import React from "react";
import "./App.css";
import { HOME_CAROUSEL_DATA } from './consts/carousel';
import Carousel, { HomeCarouselItems } from "./components/CommonCarousel";

function App() {
  return <div className="App">
    <Carousel time={4000}>
      {HOME_CAROUSEL_DATA.map(v => (
        <HomeCarouselItems
          data={v}
          key={v.id}
        />
      ))}
    </Carousel>
  </div>;
}

export default App;
