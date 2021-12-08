import React from 'react';
import Carousel from './components/Carousel';
// // 解开下一行注释可查看默认效果
// import { ICarouselItem } from './components/Carousel';
import './App.css';

import airpodsPng from './assets/airpods.png';
import iphonePng from './assets/iphone.png';
import tabletPng from './assets/tablet.png';

import { MyCarouselItem, IMyCarouselItem } from './components/MyCarouselItem';

/** 轮播图数据模型 */
interface InfoModel {
  /** 数据id */
  id: string;
  /** 图片地址 */
  image: string;
  /** 图片倍率，主要指高，可由信息录入时根据图片计算得到 */
  imageRate?: number;
  /** 主要标题 */
  title?: string | string[];
  /** 次要标题 */
  subTitle?: string | string[];
  /** 轮播项样式，可根据图片特色在录入时添加修改样式，如上传图片为浅色调，文字颜色想定为深色 */
  style?: {
    [k in string]: string;
  };
}

function App() {
  const items: InfoModel[] = [
    {
      id: '1',
      image: iphonePng,
      title: 'xPhone',
      subTitle: ['Lots to love. Less toSpend.', 'Starting at $399.'],
      style: {
        color: 'white',
        backgroundColor: '#111111',
      },
    },
    {
      id: '2',
      image: tabletPng,
      imageRate: 2,
      title: 'Tablet',
      subTitle: 'Just the right amount of everything.',
      style: {
        color: 'black',
        backgroundColor: '#fafafa',
      },
    },
    {
      id: '3',
      image: airpodsPng,
      imageRate: 2,
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
      style: {
        color: 'black',
        backgroundColor: '#f1f1f1',
      },
    },
  ];

  // //解开下方注释可查看默认效果
  // const defaultItems: ICarouselItem[] = [
  //   { url: iphonePng, title: 'xPhone', style: { color: 'white' } },
  //   { url: tabletPng, title: 'Tablet' },
  //   { url: airpodsPng, title: 'Buy a Tablet or xPhone for college' },
  // ];

  const onClickItem = (item: IMyCarouselItem) => {
    console.log('点击了轮播项，' + item.title);
  };

  return (
    <div className='App'>
      {/* 解开下方注释可查看组件默认效果 */}
      {/* <Carousel items={defaultItems} auto onClick={onClickItem}></Carousel> */}
      <Carousel auto>
        {items.map((item) => (
          <MyCarouselItem key={item.id} {...item} onClick={onClickItem} />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
