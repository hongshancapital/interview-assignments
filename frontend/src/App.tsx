import React from "react";
import "./App.css";
import { Carousel } from "./components/Carousel";
import { CarouselItem } from "./components/Carousel/component/CarouselItem";
import { CarouselInfo } from "./components/Carousel/component/CarouselInfo";
import { ICarouselImages } from "./type";

import Airpods from './assets/airpods.png';
import Iphone from './assets/iphone.png';
import Tablet from './assets/tablet.png';

function App() {

  const images: ICarouselImages[] = [
    {
      title: 'xPhone',
      desc: ['Lots to love.Less to spend.','Starting at $399'],
      image: Iphone,
      FontColor: '#FFF',
      backgroundColor: "#425066",
    },
    {
      title: 'Tablet',
      desc: 'Just the right amount of everything.',
      image: Tablet,
      FontColor: '#000',
      backgroundColor: "#1bd1a5",
    },
    {
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
      desc: '',
      image: Airpods,
      FontColor: '#000',
      backgroundColor: "#a78e44",
    },
  ]

  return <div className="App">
    {/* write your component here */}
    <Carousel switchingTime={3}>
      {
        images?.map((item) => {
          return (
            <CarouselItem
              key={item.image}
            style={{
              backgroundColor: item.backgroundColor
            }}
            >
              <CarouselInfo
                title={item.title}
                desc={item.desc}
                image={item.image}
                fontColor={item.FontColor}
              />
            </CarouselItem>
          )
        })
      }
    </Carousel>
  </div>;
}

export default App;
