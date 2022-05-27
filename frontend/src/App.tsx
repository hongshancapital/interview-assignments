import React from "react";
import "./App.css";
import Carousel from './carosel';
const { CarouselItem } = Carousel;

function App() {
    return <div className="App">
        <Carousel autoplay>
            <CarouselItem className={"MyCarouselItem"} style={{
					"backgroundColor":"#111",
					"width":"100%",
					"color":"#fff",
					"backgroundImage":`url(${require('./assets/iphone.png')})`}
			}>
              <h1>xPhone</h1>
              <p>Lots to love. Less to spend</p>
              <p>Sarting at $399</p>
            </CarouselItem>
            <CarouselItem className={"MyCarouselItem"} style={{
				"backgroundColor":"#fafafa",
				"width":"100%",
				"color":"#000",
				"backgroundImage":`url(${require('./assets/tablet.png')})`
			}}>
              <h1>Tablet</h1>
              <p>Just right amount of everything</p>
            </CarouselItem>
            <CarouselItem className={"MyCarouselItem"} style={{
				"backgroundColor":"#f1f1f3",
				"width":"100%",
				"color":"#000",
				"backgroundImage":`url(${require('./assets/airpods.png')})`
			}}>
              <h1>Buy a Tablet or xPhone for college.</h1>
              <h1>Get arPods.</h1>
            </CarouselItem>
        </Carousel>
    </div>;
}

export default App;
