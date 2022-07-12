import React, { useMemo } from "react";
import "./App.css";
import Carousel from "./component/Carousel";
import airpodsIcon from "./assets/airpods.png";
import iphoneIcon from "./assets/iphone.png";
import tabletIcon from "./assets/tablet.png";

function App() {
  const contentList = useMemo(() => {
    return [
      {
        title: "xPhone",
        des: "Lots to love. Less to spend.\nStarting at $399",
        src: iphoneIcon,
        style: {
          width: 210,
          color: "#fff",
        },
      },
      {
        title: "Tablet",
        des: "Just the right amount of everything",
        src: tabletIcon,
        style: {
          width: 300,
          color: "#000",
        },
      },
      {
        title: "Buy a Tablet or xPhone for college.\nGet arPods",
        src: airpodsIcon,
        style: {
          width: 540,
          color: "#000",
        },
      },
    ];
  }, []);

  return (
    <div className="App">
      <Carousel>
        {contentList?.map((content) => {
          return (
            <div
              key={content.title}
              className="childWrapper"
            >
              <img src={content.src} width="100%" height="100%" alt="" />
              <div className="info">
                <h1 style={content.style}>{content.title}</h1>
                <div style={content.style}>{content.des}</div>
              </div>
            </div>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
