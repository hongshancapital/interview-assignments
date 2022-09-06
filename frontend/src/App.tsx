/*
 * @Author: shiguang
 * @Date: 2022-05-17 18:04:42
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-17 19:17:07
 * @Description: carousel 页面
 */
import React from 'react';
import Carousel from './Component/Carousel';
import './App.scss';


const dataSource = [
  {
    name: 'xPhone',
    title: ['xPhone'],
    desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
  },
  {
    name: 'tablet',
    title: ['Tablet',],
    desc: ['Just the right amount of everything.'],
  },
  {
    name: 'airpods',
    title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
    desc: [],
  }
];

/**
 * 渲染 slider
 * @param item 当前 slider 配置
 * @param index 下标
 * @returns 
 */
export const renderSlider = (item: typeof dataSource[number], index: number) => {
  const textDom = (['title', 'desc'] as const).map(field => (
    <div className={`carousel-item-text-${field}`} key={field} >
      {item[field].map((children, key) => (
        <div {...{ children, key }} />
      ))}
    </div>
  ));

  return (
    <div
      className={`carousel-item ${item.name}`}
      key={index}
    >
      <div className="carousel-item-text"> {textDom}</div>
    </div>
  );
};

const sliderListDom = dataSource.map(renderSlider);

const App = () => {
  return (
    <div className="app-page">
      <Carousel duration={2000} >{sliderListDom}</Carousel>
    </div>
  );
};

export default App;
