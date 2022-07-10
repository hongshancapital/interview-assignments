import React from "react";
import "./App.css";

import Carousel from "./components/Carousel"

import img from "./assets/iphone.png";
import img1 from "./assets/tablet.png";
import img2 from "./assets/airpods.png";

const imgList = [
  { titile: 'xPhone', describe: 'Lost to love.Less to spend.<br>Starting at $399' , fontColor: '#FFFFFF', src: img , width: '300px' , height: '380px', fit: 'cover' , backColor: '#111111' , butBackColor: '#A09FA0' },
  { titile: 'Table', describe: 'Just the right amount of everything.', fontColor: '#000000', src: img1, width: '200px' , height: '360px', fit: 'cover' , backColor: '#FAFAFA', butBackColor: '#A09FA0' },
  { titile: 'Buy a Tablet or xPhone for college.<br>Get arPods.', describe: null, fontColor: '#000000',  src: img2, width: '200px' , height: '320px', fit: 'cover' , backColor: '#F1F1F3' , butBackColor: '#A09FA0' }
]

function App() {
    return <div className="App">
        <Carousel autoplay={true} imgList={ imgList } />
    </div>;
}

export default App;
