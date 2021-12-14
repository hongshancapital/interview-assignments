import React from "react";
import "./App.scss";
import {Carousel} from "./components/Carousel"

import airpods from "./assets/airpods.png"
import iphone from "./assets/iphone.png"
import tablet from "./assets/tablet.png"


function App() {
  // break line please add \n after the word
  let data: any = [
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
      textColor: 'black'},
    {
      url: airpods, 
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.', 
      titleColor: 'black'
    },
  
  ]
  let attribute: any = {
    interval: 5000, // ms
    slideTransitionTiming: 'ease',
    progressBarTransitionTiming: 'ease',
    progressBarColor: 'white',
    barClickChangeImg: true // can click prgress bar to change the slide
    /* 
    ease: increases in velocity towards the middle of the transition, slowing back down at the end.
    linear: transitions at an even speed.
    ease-in: starts off slowly, with the transition speed increasing until complete.
    ease-out: starts transitioning quickly, slowing down the transition continues.
    ease-in-out: starts transitioning slowly, speeds up, and then slows down again.
    */
  }

  return (<div className="App">
    <Carousel data={data} attr={attribute} />
    </div>);
}

export default App;
