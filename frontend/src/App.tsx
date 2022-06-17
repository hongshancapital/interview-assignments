import React, { useMemo, useState } from 'react';
import './App.css';
import Carousel from './components/Carousel';
import CarouselContent from './components/CarouselContent';
import CarouselItem from './components/CarouselItem';
import { infoItemType } from './interface';

// 轮播图数据
const info: Array<infoItemType> = [
  {
    title: 'xPhone',
    description: 'Lots to Love.Less to spend.Starting at $399.',
    imgUrl:
      'https://www.apple.com.cn/iphone-12/images/meta/wechat/iphone-12_specs__ffy8c0h8c2ai_og.png',
    fontColor: '#fff',
    customBackgroundColor: '#000',
    indicatorBackgroundColor: '#a3a3a3',
  },
  {
    title: 'Tablet',
    description: 'Just the right amount of everything.',
    imgUrl:
      'https://www.apple.com.cn/v/ipad-pro/ai/images/specs/12_9_in_ipad_pro__dqrvlzepem4i_large_2x.jpg',
    fontColor: '#000',
    customBackgroundColor: '#fff',
    indicatorBackgroundColor: '#000',
  },
  {
    title: 'Buy a Tablet or xPhone for college. \n Get arPods.',
    imgUrl:
      'https://www.apple.com.cn/v/airpods/compare/b/images/overview/airpods_3gen_white__car4z3m37ss2_large_2x.png',
    fontColor: '#000',
    customBackgroundColor: '#a3a3a3',
    indicatorBackgroundColor: '#000',
  },
];

const CarouselContentDomMemo = React.memo(
  ({
    title,
    description,
    imgUrl,
    fontColor,
    customBackgroundColor,
  }: infoItemType) => {
    return (
      <CarouselItem customBackgroundColor={customBackgroundColor}>
        <CarouselContent {...{ title, description, imgUrl, fontColor }} />
      </CarouselItem>
    );
  }
);

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
          return <CarouselContentDomMemo {...item} />;
        })}
      </Carousel>
    </div>
  );
};

export default App;
