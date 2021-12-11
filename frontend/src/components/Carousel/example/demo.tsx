import React from 'react';
import { Carousel, CarouselItem, defineCarouselProps } from '../';
import './demo.scss';

import airpods from '../../../assets/airpods.png';
import iphone from '../../../assets/iphone.png';
import tablet from '../../../assets/tablet.png';

const imgs = [
  {
    url: iphone,
    title: 'xPhone',
    desc: ['lots to love.Less to spend.\nStaring at $399'],
    cls: 'carousel-demo-slide dark',
  },
  { url: tablet, title: 'Tablet', desc: 'Just the right amount of everything', cls: 'carousel-demo-slide light' },
  {
    url: airpods,
    title: ['Buy a Tablet or xPhone for college.\nGet airPods'],
    cls: 'carousel-demo-slide light',
  },
];

const CarouselItems = imgs.map((it, index) => {
  return (
    <CarouselItem key={index}>
      <div className={it.cls} style={{ backgroundImage: `url(${it.url})` }}>
        {it.title && <div className="title">{it.title}</div>}
        {it.desc && <div className="desc">{it.desc}</div>}
      </div>
    </CarouselItem>
  );
});

const carouselProps = defineCarouselProps({
  width: '100vw',
  height: '100vh',
  showIndicator: true,
  showNavi: true,
  overPause: true,
});

const CarouselDemo = () => {
  return (
    <Carousel {...carouselProps} animation="slideLeft" duration={3000}>
      {CarouselItems}
    </Carousel>
  );
};

export default CarouselDemo;
