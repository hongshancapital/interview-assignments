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
      {/* write your component here */}
      <Carousel>
        {DATA.map((v, index) => (
          <div
            className="img-wrapper"
            style={{ backgroundImage: `url(${v.src})` }}
            key={index}
          >
            {v.title.map((item) => (
              <h3>{item}</h3>
            ))}
            {v.content?.map((item) => (
              <p>{item}</p>
            ))}
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
