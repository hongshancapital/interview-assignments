import React from "react";
import Carousel, { CarouselItem } from './components/Carousel';
import { Iphone, Tablet, Airpods } from "./components/BannerItems";
import "./App.css";

function App() {
  const items: CarouselItem[] = [
    { id: 1, component: Iphone },
    { id: 2, component: Tablet },
    { id: 3, component: Airpods }
  ];;

  return (
    <div className="App">
      <Carousel items={items} time={3000} />
    </div>
  );
}

export default App;
