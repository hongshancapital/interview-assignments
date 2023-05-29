import { FC } from 'react';
import Carousel from '../components/carousel';
import Card, { CardTheme } from '../components/card';
import './index.css';

// 轮播数据，如果接口返回，可抽离为service层
const list = [
  {
    theme: CardTheme.Dark,
    title: 'xPhone',
    subTitle: (
      <>
        <div>Lots to Love. Less to spend.</div>
        <div>Starting at $399.</div>
      </>
    ),
    imgUrl: require('../assets/iphone.png')
  },
  {
    theme: CardTheme.Light,
    title: 'Tablet',
    imgUrl: require('../assets/tablet.png'),
  },
  {
    theme: CardTheme.Gray,
    title: (
      <>
        <div>Buy a Tablet or XPhone for college.</div>
        <div>Get arPods.</div>
      </>
    ),
    imgUrl: require('../assets/airpods.png')
  }
];

// Carousel卡片
const CarouselCard: FC = () => {
  return (
    <Carousel autoplay className="carousel-card">
      {
        list.map((item, index) => <Card {...item} key={`carousel-item-${index}`} />)
      }
    </Carousel>
  );
};

export default CarouselCard;
