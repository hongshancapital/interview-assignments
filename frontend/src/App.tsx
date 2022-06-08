import React from "react"
import { Carousel, CarouselSlide } from "./components/Carousel"
import { AirPodsPage } from './presentation/AirPodsPage'
import { PhonePage } from "./presentation/PhonePage"
import { TabletPage } from "./presentation/TabletPage"
import "./App.css"

function App() {
  return (
    <div className="App">
      <Carousel>
        <CarouselSlide>
          <PhonePage />
        </CarouselSlide>
        <CarouselSlide>
          <TabletPage />
        </CarouselSlide>
        <CarouselSlide>
          <AirPodsPage />
        </CarouselSlide>
      </Carousel>
    </div>
  );
}

export default App
