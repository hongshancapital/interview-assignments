import React from "react";
import Carousel from './components/Carousel'
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import "./App.css";

function App() {
  return <div className="App">
    <Carousel list={[airpods, iphone, tablet]} />
    {/* write your component here */}
  </div>;
}

export default App;
