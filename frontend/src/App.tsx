import React from "react";
import "./App.scss";
import {Carousel} from "./components/Carousel"

import airpods from "./assets/airpods.png"
import iphone from "./assets/iphone.png"
import tablet from "./assets/tablet.png"
import { IAttribute, IImageData } from "./components/interface";


function App() {
  // break line please add \n after the word
  let data: Array<IImageData> = [
    {
      url: iphone, 
      title: 'xPhone', 
      titleColor: 'white', 
      text: 'Lots to Love. Less to spend.\nStarting at $399.', 
      textColor: 'white'
    },
    {
      url: tablet, 
      title: 'Tablet', 
      titleColor: 'black', 
      text: 'Just the right amount of everything.', 
      textColor: 'black'
    },
    {
      url: airpods, 
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.', 
      titleColor: 'black'
    },
  ]
  // can add more slide class or progress bar class in attributeStyle.scss
  let attribute: IAttribute = {
    interval: 5000,
    slideTransitionClassName: 'leftEaseFiveSeconds',
    progressBarClassName: 'widthWhiteEaseFiveSeconds',
    barClickChangeImg: true
  } 

  return (<div className="App">
    <Carousel data={data} attr={attribute} />
    </div>);
}

export default App;
