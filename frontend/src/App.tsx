import React from "react";
import "./App.css";
import { Carousel } from './components/carousel'
import img1 from './assets/iphone.png'
import img2 from './assets/tablet.png'
import img3 from './assets/airpods.png'
const imgs: string[] = [img1, img2, img3]
function App() {
  return <div className="App">
    <Carousel imgs={imgs} speed={3000} />
  </div>;
}

export default App;
