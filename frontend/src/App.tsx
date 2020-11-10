import React from "react";
import "./App.css";

import Carousel from './components/carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

let carouselContents = [
  {
    title: 'xPhone',
    text: 'Losts to Love.Less to Spend.\r\nStarting at $399.',
    textColor: 'rgb(17,17,17)',
    backColor: 'rgb(241,241,243)',
    img: airpods
  },
  {
    title: 'Tablet',
    text: 'Just the right amount of everything',
    textColor: 'rgb(241,241,243)',
    backColor: 'rgb(17,17,17)',
    img: iphone
  },
  {
    title: 'Buy a Tablet or xPhone for college.\r\nGet arPods.',
    text: '',
    textColor: 'rgb(17,17,17)',
    backColor: 'rgb(250,250,250)',
    img: tablet
  }
]

function App() {
  return <div className="App">
    {/* write your component here */}
    <Carousel dots={true} autoPlay duration={2000} dotPosition={'bottom'} beforeChange={(i:number) => {console.log(i)}} afterChange={(i: number) => {console.log(i)}} >
      {
        carouselContents.map((content) => {
          return (
            <div key={content.title} style={{height: '600px',display: 'flex',justifyContent: 'center',alignItems: 'cneter',flexDirection: 'column',backgroundColor: content.backColor, color: content.textColor}}>
              <h2 dangerouslySetInnerHTML={{__html: content.title && content.title.replace(/\r\n/g, '<br/>')}}></h2>
              <p dangerouslySetInnerHTML={{__html: content.text && content.text.replace(/\r\n/g, '<br />')}}></p>
              <div style={{margin: "0 auto",backgroundSize: 'cover', backgroundPosition: 'center',backgroundRepeat: 'no-repeat', width: '560px', height: '360px',backgroundColor: content.backColor, color: content.textColor, backgroundImage: `url(${content.img})`}} ></div>
            </div>
          )
        })
      }
    </Carousel>
  </div>;
}

export default App;
