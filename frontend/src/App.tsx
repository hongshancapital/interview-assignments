import React, { useMemo, useState } from 'react';
import './App.css';
import Carousel from './components/Carousel';
import CarouselContent from './components/CarouselContent';
import CarouselItem from './components/CarouselItem';
import { infoItemType } from './interface';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

// 轮播图数据
const info: Array<infoItemType> = [
  {
    title: 'xPhone',
    description: 'Lots to Love.Less to spend.Starting at $399.',
    imgUrl: iphone,
    fontColor: '#fff',
    customBackgroundColor: '#000',
    indicatorBackgroundColor: '#a3a3a3',
  },
  {
    title: 'Tablet',
    description: 'Just the right amount of everything.',
    imgUrl: tablet,
    fontColor: '#000',
    customBackgroundColor: 'rgba(249, 249, 249, 1)',
    indicatorBackgroundColor: 'rgba(160,159,159,1)',
  },
  {
    title: 'Buy a Tablet or xPhone for college. \n Get arPods.',
    imgUrl: airpods,
    fontColor: '#000',
    customBackgroundColor: 'rgba(239, 239,241,1)',
    indicatorBackgroundColor: 'rgba(160,159,159,1)',
  },
];

const App = () => {
  const [infoState] = useState(info);
  const indicatorColorList = useMemo(
    () => infoState.map((element) => element.indicatorBackgroundColor),
    [infoState]
  );
  return (
    <div className='App'>
      <Carousel indicatorColorList={indicatorColorList}>
        {infoState?.map((item, index) => {
          const {
            title,
            description,
            imgUrl,
            fontColor,
            customBackgroundColor,
          }: infoItemType = item;
          return (
            <CarouselItem
              customBackgroundColor={customBackgroundColor}
              key={`CarouselItem-${index}`}
            >
              <CarouselContent {...{ title, description, imgUrl, fontColor }} />
            </CarouselItem>
          );
        })}
      </Carousel>
    </div>
  );
};

export default App;
