import React from "react";
import "./App.css";

import Carousel from "./components/Carousel";
import Img1 from "./assets/iphone.png"
import Img2 from "./assets/tablet.png"
import Img3 from "./assets/airpods.png"

const listData = [
  {
    id: 1,
    title: 'XPhone',
    subTitle: `Lots to love.Less to spend. 
    Starting at $399.`,
    img: Img1,
    color: '#fff',
  },
  {
    id: 2,
    title: 'Tablet',
    subTitle: `Just the right amount of everything.`,
    img: Img2,
    color: '#333',
  },
  {
    id: 3,
    title: `Buy a Tablet or xPhone for college.
    Get arPods.`,
    img: Img3,
    color: '#333',
  },
]

interface BannerItemProps {
  title: string; 
  subTitle?: string; 
  img: string; 
  color: string;
}

function BannerItem(props: BannerItemProps){
  const {title, subTitle, img, color} = props
  return (
    <div 
      className="banner-item"
      style={{
        backgroundImage: `url(${img})`,
        color: color,
      }}
    >
      <h1>{title}</h1>
      <div>{subTitle}</div>
    </div>
  )
}

export default function CarouselDemo(){
  return (
    <Carousel>
      {
        listData.map(item => (
          <BannerItem {...item} key={item.id}
          />
        ))
      }
      
    </Carousel>
  )
}