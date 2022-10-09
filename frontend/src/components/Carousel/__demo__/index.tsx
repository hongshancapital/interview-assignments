import React from "react";
import style from "./styles.module.css";

import Carousel from "..";
import Img1 from "../../../assets/iphone.png"
import Img2 from "../../../assets/tablet.png"
import Img3 from "../../../assets/airpods.png"

export interface BannerItemProps {
  id: number | string;
  title: string; 
  subTitle?: string; 
  img: string; 
  color: string;
}

export const listData:BannerItemProps[] = [
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


export function BannerItem(props: BannerItemProps){
  const {title, subTitle, img, color} = props
  return (
    <div 
      className={style['banner-item']}
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