import React from "react";

import Carousel, { RemoteData } from "./components/Carousel";

import "./App.css";


const pageJson: Array<RemoteData> = [
  {
    id: "1",
    titleList: ["xPhone"],
    subTitleList: ["Lots to love.less to spend","Starting at $399."],
    backgroundColor:'black',
    backgroundImage: `url(${require('./assets/iphone.png').default})`,
    color: "white",
  },
  {
    id: "2",
    titleList: ["Table"],
    subTitleList: ["just the right amount of everything."],
    backgroundImage: `url(${require('./assets/tablet.png').default})`,
  },
  {
    id: "3",
    titleList: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    subTitleList: [],
    backgroundImage: `url(${require('./assets/airpods.png').default})`,
  },
];

function App() {
  return (
    <div className="App">
      <Carousel list={Carousel.buildPartSample(pageJson)} />
    </div>
  );
}

export default App;
