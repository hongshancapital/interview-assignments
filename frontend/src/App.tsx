import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpod from './assets/airpods.png';


function App() {
  const products = [
    { title: "xPhone", subTitle: 'Lots to love. Less to spend. \n Starting at $399', imgUrl: iphone, style: { color: '#FFFFFF', backgroundColor: 'rgb(17,17,17)'},  },
    { title: "Tablet", subTitle: 'Just the right amount of everything.', imgUrl: tablet, style: {backgroundColor: 'rgb(250, 250, 250)'} },
    { title: "Buy a Tablet or xPhone for college. \n Get arPords.", subTitle: '', imgUrl: airpod, style: {backgroundColor: 'rgb(241, 241, 243)'}},
  ]
  return <div className="App">
    <Carousel products={products} delay={2000} />
  </div>;
}

export default App;
