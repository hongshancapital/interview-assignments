import React from "react";
import "./App.scss";
import { Carousel } from "./components";
import { Banner } from './models';
import phoneImg from '../src/assets/iphone.png'
import tabletImg from '../src/assets/tablet.png'
import airpodsImg from '../src/assets/airpods.png'

function App() {
  const bannerList: Array<Banner> = [
    {
      imageUrl: phoneImg,
      title: 'xPhone',
      description: 'Lots to love. less to spend\nStarting at $399',
      color: '#fff'
    },
    {
      imageUrl: tabletImg,
      title: 'Tablet',
      description: 'Just the right amount of everything.',
      color: '#000'
    },
    {
      imageUrl: airpodsImg,
      title: 'Buy a Tablet or xPhone for college\nGet airPods.',
      color: '#000'
    }
  ];

  return <div className="App">
    <Carousel>
      {bannerList.map((item, index) => {
        return <div className="carousel-item__content" key={index} style={{ backgroundImage: `url(${item.imageUrl})`, color: item.color }}>
                  <h1>{item.title}</h1>
                  <div className="description">{item.description}</div>
              </div>
      })}
    </Carousel>
  </div>;
}

export default App;
