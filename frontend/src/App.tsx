import React from "react";
import Carousel from "./components/Carousel";

import "./App.css";

import airpodsImg from "./assets/airpods.png";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";

function App() {
  const data = [
    {
      // backgroundColor: "#000",
      backgroundImage: iphoneImg,
      color: "#fff",
      mainText: ["xPhone"],
      subText: ["Lots to Love.Less to spend.", "Starting at $399."],
      // img: "https://www.apple.com/v/iphone/home/bc/images/overview/compare/compare_iphone_13__ejsyn7suyw02_small_2x.jpg",
    },
    {
      // backgroundColor: "#fff",
      backgroundImage: tabletImg,
      color: "#000",
      mainText: ["Tablet"],
      subText: ["Just the right amount of everything"],
      // img: "https://www.apple.com/v/iphone/home/bc/images/overview/compare/compare_iphone_13__ejsyn7suyw02_small_2x.jpg",
    },
    {
      // backgroundColor: "#ccc",
      backgroundImage: airpodsImg,
      color: "#000",
      mainText: ["Buy a Tablet or xPhone for colleage.", "Get airPods."],
      // img: "https://www.apple.com/v/iphone/home/bc/images/overview/compare/compare_iphone_13__ejsyn7suyw02_small_2x.jpg",
    },
  ];

  return (
    <div className="App">
      <Carousel duration={3000}>
        {data.map((item) => (
          <div
            key={item.mainText.join("")}
            className="item"
            style={{
              color: item.color,
              backgroundImage: `url(${item.backgroundImage})`,
            }}
          >
            <div className="desc">
              {item.mainText?.map((text) => (
                <p key={text} className="mainText">
                  {text}
                </p>
              ))}
              {item.subText?.map((text) => (
                <p key={text} className="subText">
                  {text}
                </p>
              ))}
            </div>
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
