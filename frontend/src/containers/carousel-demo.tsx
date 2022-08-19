import React from 'react';

import Carousel from '../components/carousel';
import { DataItem, THEME_TYPE, ICON_NAME } from '../components/index.d';
import './carousel-demo.css';

const data:DataItem[] =[{
  id:1,
  title: ['xPhone'],
  description: ['Lots to love. Less to spend.',' Starging at $399.'],
  icon: ICON_NAME.IPHONE,
  themeType: THEME_TYPE.BLACK,
},{
  id:2,
  title: ['Tablet'],
  description: ['Just the right amount of everything'],
  icon: ICON_NAME.TABLET,
  themeType: THEME_TYPE.WHITE,
},{
  id:3,
  title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
  icon: ICON_NAME.AIRPODS,
  themeType: THEME_TYPE.GREY,
}];

export default function CarouselDemo(){
  return (
    <div className="carousel-demo-wrapper">
      <Carousel data={data} speed={3000}/>
    </div>
  )
}