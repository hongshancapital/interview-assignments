import React from "react";
import Carousel from "./components/Carousel";
import "./App.css";

interface ImagProps {
  src: string,
  alt: string,
}

function App() {
  const bannerList:Array<ImagProps> = [
    {
      src: require('./assets/airpods.png'),
      alt: 'images-1',
    },
    {
      src: require('./assets/iphone.png'),
      alt: 'images-2',
    },
    {
      src: require('./assets/tablet.png'),
      alt: 'images-3',
    },
  ];

  return <div className="App">
    <Carousel bannerList={bannerList} />
  </div>;
}

export default App;
