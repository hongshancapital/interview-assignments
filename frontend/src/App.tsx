import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import { CarouselItemProps } from "./components/CarouselItem";


const list: CarouselItemProps[] = [
    {
        url: iphone,
        alt: 'iphone',
        title: <p style={{color: '#fff', fontSize: '28px'}}>xPhone</p>,
        desc: <>
          <p style={{color: '#fff', fontSize: '14px'}}>Lots to love. Less to speed. </p>
          <p style={{color: '#fff', fontSize: '14px'}}>Starting at $399'</p>
        </>
    },
    {
      url: tablet,
      alt: 'tablet',
      title: <p style={{fontSize: '28px'}}>Tablet</p>,
      desc: <p style={{fontSize: '14px'}}>Just the right amount of ervrything.</p>,
    },
    {
      url: airpods,
      alt: 'airpods',
      title: <p style={{fontSize: '28px'}}>Buy a Tablet or xPhone for college</p>,
      desc: <p style={{fontSize: '28px'}}>Get arPods.</p>,
    }
] 

// const content = <CarouselItem url={""} style={undefined} alt={""}/>

function App() {
  return <div className="App">
      <Carousel list={list}/>
    </div>;
}

export default App;
