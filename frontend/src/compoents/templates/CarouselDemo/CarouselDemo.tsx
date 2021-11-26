import React, {FC} from "react";
import {Carousel, CarouselProps} from "../../molecules";

import airpods from "../../../assets/airpods.png";
import iphone from "../../../assets/iphone.png";
import tablet from "../../../assets/tablet.png";
import css from './CarouselDemo.module.scss';

export const products = [
  {
    url: iphone, title: 'xPhone',
    desc: 'Lots to love. Less to spend.',
    bgColor: '#111111',
    color: '#fff',
  },
  {
    url: tablet,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    bgColor: '#FAFAFA',
  },
  {
    url: airpods,
    title: 'Buy a Tablet or xPhone for college. \nGet airPods.',
    desc: '',
    bgColor: '#F1F1F3',
  },
]

export const CarouselDemo: FC<CarouselProps> = (props) => {
  return <>
    <div style={{ height: '10vh' }}>
      <h3>Blank for testing mouse suspension pause playback</h3>
    </div>
    <div style={{ width: '100%' }}>
      <Carousel {...props}>
      {
        products.map((item, idx) => {
          return <div key={idx} className={css['carousel-item']} style={{ backgroundColor: item.bgColor }}>
            <img src={item.url} alt=""/>
            <div className={css['summary']}>
              <div style={{ color: item.color }} className={css['title']}>{item.title}</div>
              <div style={{ color: item.color }} className={css['desc']}>{item.desc}</div>
            </div>

          </div>
        })
      }
    </Carousel>
    </div>
  </>
}