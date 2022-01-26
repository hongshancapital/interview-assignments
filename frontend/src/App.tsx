import React, { useRef } from "react";
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";
import "./App.css";

type CarouselHandle = React.ElementRef<typeof Carousel>;
interface CarouseDataProps {
  url: string;
  title?: string[];
  text?: string[];
  color?: string;
}

function App() {
  const carControl = useRef<CarouselHandle>(null);
  const carouseData: CarouseDataProps[] = [
    {
      url: iphone,
      title: ["xPhone"],
      text: ["Lots to love. Less to spend."],
      color: "#fff",
    },
    {
      url: tablet,
      title: ["Tablet"],
      text: ["Just the right amount of everything."],
      color: "#000",
    },
    {
      url: airpods,
      title: ["Buy a Tablet or xPhone for college.", "Get airPods"],
      color: "#000",
    },
  ];
  return (
    <div className="App">
      <div className="carousel-wrap">
        <Carousel autoPlay={true} ref={carControl}>
          {carouseData.map(({ url, title = [], text = [], color }, i) => {
            console.log(url, "url");
            return (
              <div
                className="item-wapper"
                style={{ backgroundImage: `url(${url})` }}
                key={i}
              >
                <div className="text-wrapper">
                  {title.map((tit, titIndex) => (
                    <div className="title" key={titIndex} style={{ color }}>
                      {tit}
                    </div>
                  ))}
                  {text.map((t, tIndex) => (
                    <div className="text" key={tIndex} style={{ color }}>
                      {t}
                    </div>
                  ))}
                </div>
              </div>
            );
          })}
        </Carousel>
      </div>
      {/* <div className="flex-rows gap-8 justify-content-center mt-2">
        <button className="btn" onClick={() => carControl.current?.prev()}>
          上一条
        </button>
        <button className="btn" onClick={() => carControl.current?.next()}>
          下一条
        </button>
        <button className="btn" onClick={() => carControl.current?.go(1)}>
          跳到第2条
        </button>
      </div> */}
    </div>
  );
}

export default App;
