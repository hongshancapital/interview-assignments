import React from "react";
import { Carousel } from "./components"
import "./App.css";

import airpodsImg from "./assets/airpods.png";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";

const mockList = [
  {
    title: "xPhone",
    src: iphoneImg,
    content: "Lots to love. Less to spend. \n Starting at $399.",
  },
  {
    title: "Tablet",
    src: tabletImg,
    content: "Just the right amount of everything.",
  },
  {
    title: "Buy a Tablet or xPhone for College.\n Get arPods.",
    src: airpodsImg,
  },
];

function App() {
  return <div className="App">
    <Carousel>
      {
        mockList.map((item, index) => {
          return <div
            key={index}
            className="img-wrapper"
            style={{ backgroundImage: `url(${item.src})` }}
          >
            <h3 className="title">{item.title}</h3>
            <p className="text">{item.content}</p>
          </div>
        })
      }
    </Carousel>
  </div>;
}

export default App;
