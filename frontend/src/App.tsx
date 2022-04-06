import React from "react";
import Carousel from "./components/Carousel/index";
import "./App.css";

import iphoneImage from './assets/iphone.png'
import tabletImage from './assets/tablet.png'
import airpodsImage from './assets/airpods.png'

const posters = [
  {
    alt: 'xPhone',
    title: 'xPhone',
    subtitle: 'Lots to love. Less to spend. Starting at $399.',
    color: '#fff',
    bgColor: '#111111',
    src: iphoneImage
  },
  {
    alt: 'tablet',
    title: 'Tablet',
    subtitle: 'Just the right amount of everything.',
    color: '#000',
    bgColor: '#fafafa',
    src: tabletImage
  },
  {
    alt: 'airpods',
    title: 'Buy a Tablet or xPhone for college. \n Get arPods.',
    subtitle: '',
    color: '#000',
    bgColor: '#f1f1f3',
    src: airpodsImage
  }
]

function App() {
  const imgStyle = {
    maxHeight: '100%',
    maxWidth: '100%'
  }

  return <div className="App">{
    /* write your component here */
    <Carousel>
      {
        posters.map((item, idx) => 
          <Carousel.Item key={idx} bgColor={item.bgColor}>
            <Carousel.Caption title={item.title} subtitle={item.subtitle} color={item.color}/>
            <img style={imgStyle} src={item.src} alt={item.alt}/>
          </Carousel.Item>  
        )
      }
    </Carousel>
  }</div>;
}

export default App;
