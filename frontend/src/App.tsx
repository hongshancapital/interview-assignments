import React from "react";
import "./App.css";
import { Carousel, Progress } from "./components/Carousel";
import xPhone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";

function App() {

  const renderData = [
    {
      bgColor: 'black',
      textColor: 'white',
      title: 'xPhone',
      subtitle: 'Lots to love. Less to spend. Starting at $399.',
      imgUrl: xPhone
    },
    {
      bgColor: 'white',
      textColor: 'black',
      title: 'Tablet',
      subtitle: 'Just the right amount of everything.',
      imgUrl: tablet
    },
    {
      bgColor: 'grey',
      textColor: 'black',
      title: 'Buy a Tablet or xPhone for college. Get arPods.',
      imgUrl: airpods
    },

  ]
  return <div className="App" style={{ "marginTop": '300px' }}>
    {/* <Carousel data={renderData} /> */}
    {
      Progress(3)
    }
  </div>;
}

export default App;
