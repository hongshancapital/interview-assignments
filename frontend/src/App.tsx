import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import { CarouselItemData } from "./components/interface";


const list: CarouselItemData[] = [
    {
        url: iphone,
        alt: 'iphone',
        title: <p style={{color: '#fff', fontSize: '28px'}}>xPhone</p>,
        desc: <>
          <p style={{color: '#fff', fontSize: '14px'}}>Lots to love. Less to speed. </p>
          <p style={{color: '#fff', fontSize: '14px'}}>Starting at $399'</p>
        </>,
        backgroundColor: '#111'
    },
    {
      url: tablet,
      alt: 'tablet',
      title: <p style={{fontSize: '28px'}}>Tablet</p>,
      desc: <p style={{ fontSize: '14px' }}>Just the right amount of ervrything.</p>,
      backgroundColor: '#fafafa'
    },
    {
      url: airpods,
      alt: 'airpods',
      title: <p style={{fontSize: '28px'}}>Buy a Tablet or xPhone for college</p>,
      desc: <p style={{ fontSize: '28px' }}>Get arPods.</p>,
      backgroundColor: '#f1f1f3'
    }
]

// const content = <CarouselItem url={""} style={undefined} alt={""}/>

function App() {
  return <div className="App">
    <Carousel list={list} width={'100vw'} height={'100vh'} />
    </div>;
}

export default App;
