import React from "react";
import "./assets/styles/App.scss";
import Phone from "./assets/imgs/iphone.png";
import Tablet from "./assets/imgs/tablet.png";
import Airpods from "./assets/imgs/airpods.png";
import Carousel from "./components/carousel";

function App() {
  return (
    <div className="App">
      <p>loop:false | 480*300</p>
      <Carousel
        loop={false}
        width={480}
        height={300}
        style={{ margin: "0 auto" }}
      >
        <div
          className="my_carousel_item _white"
          style={{
            backgroundImage: `url(${Phone})`,
          }}
        >
          <div className="description">
            <h3>xPhone</h3>
            <p>Lots to love. Less to spend</p>
            <p>Starting at $399</p>
          </div>
        </div>
        <div
          className="my_carousel_item"
          style={{
            backgroundImage: `url(${Tablet})`,
          }}
        >
          <div className="description">
            <h3>Tablet</h3>
            <p>Just the right amount of everything</p>
          </div>
        </div>
        <div
          className="my_carousel_item"
          style={{
            backgroundImage: `url(${Airpods})`,
          }}
        >
          <div className="description">
            <h3>Buy a Tablet or xPhone for collge</h3>
            <h3>Get arPods</h3>
          </div>
        </div>
      </Carousel>
      <p>controls:false | 690*600</p>
      <Carousel
        controls={false}
        width={960}
        height={600}
        style={{ margin: "0 auto" }}
      >
        <div
          className="my_carousel_item my_carousel_item2 _white"
          style={{
            backgroundImage: `url(${Phone})`,
          }}
        >
          <div className="description">
            <h3>xPhone</h3>
            <p>Lots to love. Less to spend</p>
            <p>Starting at $399</p>
          </div>
        </div>
        <div
          className="my_carousel_item my_carousel_item2"
          style={{
            backgroundImage: `url(${Tablet})`,
          }}
        >
          <div className="description">
            <h3>Tablet</h3>
            <p>Just the right amount of everything</p>
          </div>
        </div>
        <div
          className="my_carousel_item my_carousel_item2"
          style={{
            backgroundImage: `url(${Airpods})`,
          }}
        >
          <div className="description">
            <h3>Buy a Tablet or xPhone for collge</h3>
            <h3>Get arPods</h3>
          </div>
        </div>
      </Carousel>
      <p>autoplay:false | indicator:false | 1440*900</p>
      <Carousel
        autoplay={false}
        indicator={false}
        width={1440}
        height={900}
        style={{ margin: "0 auto" }}
      >
        <div
          className="my_carousel_item my_carousel_item3 _white"
          style={{
            backgroundImage: `url(${Phone})`,
          }}
        >
          <div className="description">
            <h3>xPhone</h3>
            <p>Lots to love. Less to spend</p>
            <p>Starting at $399</p>
          </div>
        </div>
        <div
          className="my_carousel_item my_carousel_item3"
          style={{
            backgroundImage: `url(${Tablet})`,
          }}
        >
          <div className="description">
            <h3>Tablet</h3>
            <p>Just the right amount of everything</p>
          </div>
        </div>
        <div
          className="my_carousel_item my_carousel_item3"
          style={{
            backgroundImage: `url(${Airpods})`,
          }}
        >
          <div className="description">
            <h3>Buy a Tablet or xPhone for collge</h3>
            <h3>Get arPods</h3>
          </div>
        </div>
      </Carousel>
      <br />
      <br />
      <br />
    </div>
  );
}

export default App;
