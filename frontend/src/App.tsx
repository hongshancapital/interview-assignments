import React from "react";
import "./App.scss";
import { Carousel } from "./components";
import airpodsImg from "./assets/airpods.png";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";

const DATA = [
  {
    title: ["xPhone"],
    src: iphoneImg,
    content: ["Lots to love. Less to spend", "Starting at $399"],
  },
  {
    title: ["Tablet"],
    src: tabletImg,
    content: ["Just the right amount of everything."],
  },
  {
    title: ["Buy a Tablet or xPhone for College.", "Get arPods"],
    src: airpodsImg,
  },
];
function App() {
  return (
    <div className="App">
      <Carousel>
        {DATA.map((v, index) => (
          <div
            key={index}
            className="img-wrapper"
            style={{ backgroundImage: `url(${v.src})` }}
          >
            {v.title.map((item, index) => (
              <h3 key={index}>{item}</h3>
            ))}
            {v.content?.map((item, index) => (
              <p key={index}>{item}</p>
            ))}
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
