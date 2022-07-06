import React from 'react';
import './App.css';

import Carousel from './components/Carousel/Carousel';
import SlideOne from './components/Carousel/carouselSlides/SlideOne';
import SlideTwo from './components/Carousel/carouselSlides/SlideTwo';
import SlideThree from './components/Carousel/carouselSlides/SlideThree';

/**
 * @description 这是项目起始组件
 * 通过React Hooks实现Carousel组件；以这种方式设计Carousel组件的原因是：
 * 最大限度的实现Carousel组件的复用。如果在其他场景，需要使用该组件时，只需要传入不同的幻灯片(子组件)就可以实现对应的功能
 * 这是1.0版本的组件
 * @module App
 * @version 1.0
 * @author JinBin Li
 * @param {number} 自动轮播的时间间隔，以秒为单位
 * @return {Carousel} 项目组件
 */

function App() {
  return (
    <div className="App">
      <Carousel autoPlay={3}>
        <SlideOne></SlideOne>
        <SlideTwo></SlideTwo>
        <SlideThree></SlideThree>
      </Carousel>
    </div>
  );
}

export default App;
