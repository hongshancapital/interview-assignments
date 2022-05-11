import React from "react";
import { Carousel, Item } from './components/Carousel'
import "./App.css";
import imgIphone from './assets/iphone.png'
import imgTablet from './assets/tablet.png'
import imgAir from './assets/airpods.png'

function App() {

  const Items = [
    {
      titles: ['xPhone'],
      texts: [
        'Lots to love. Less to spend.',
        'Starting at $399.'
      ],
      background: '#111',
      color: "#fff",
      imgSrc: imgIphone,
      imgWidth: "700px"
    },
    {
      titles: ['tablet.'],
      texts: ['Just the right amount of everything.'],
      background: 'rgb(250, 250, 250)',
      color: "#111",
      imgSrc: imgTablet,
      imgWidth: "1100px"
    },
    {
      titles: [
        'Buy a tablet or xPhone for college.',
        'Get arPods.'
      ],
      texts: ['Just the right amount of everything.'],
      background: 'rgb(241, 241, 243)',
      color: "#111",
      imgSrc: imgAir,
      imgWidth: "1700px"
    }
  ]

  return <div className="App">
    <Carousel
      height="100vh"
      width="100vw"
      items={Items.map(item => <Item {...item} />)}
    />
  </div>;
}

export default App;
