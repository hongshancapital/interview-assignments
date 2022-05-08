import React from "react";
import "./App.css";
import Carousel, { CarouselItem } from "./carousel/index";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

function App() {
  // return <div className="App">{/* write your component here */}</div>;
  const style1 = {
    backgroundColor: "#111",
    color: "#fff",
  };
  const style2 = {
    backgroundColor: "rgb(251, 250, 250)",
    color: "#000",
  };
  const style3 = {
    backgroundColor: "#f1f1f3",
  };
  return (
      <div className="App">
        <Carousel dots={true} duration={3000} speed={300}>
          <CarouselItem style={style1}>
            <div className="img-box">
              <img src={iphoneImg} alt="显示图" />
            </div>
          </CarouselItem>
          <CarouselItem style={style2}>
            <div className="img-box">
              <img src={tabletImg} alt="显示图" />
            </div>
          </CarouselItem>
          <CarouselItem style={style3}>
            <div className="img-box">
              <img src={airpodsImg} alt="显示图" />
            </div>
          </CarouselItem>
        </Carousel>
      </div>
  );
}

export default App;
