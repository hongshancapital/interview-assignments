import React from "react";
import "./App.css";
import Carousel, { ListType } from "./components/Carousel";
import xPhoneImage from './assets/iphone.png';
import tabletImage from './assets/tablet.png';
import airpodsImage from './assets/airpods.png';
/**
 * 三张图尺寸有点不一样，若想铺满屏幕，使用background-size: cover; 
 * 若想图片中的设备等高，使用background-size: auto 100%;
 * 
 */

const list: ListType[] = [
    {
        id: 1,
        title: 'xPhone',
        desc: 'Lots to love.Less to spend.<br />Starting at $399.',
        url: xPhoneImage,
        color: '#fff'
    },
    {
        id: 2,
        title: 'Tablet',
        desc: 'Just the right amount of everything',
        url: tabletImage,
        color: 'black'
    },
    {
        id: 3,
        title: 'Buy a Tablet or xPhone for college.<br />Get airPods.',
        desc: '',
        url: airpodsImage,
        color: 'black'
    },
]


function App() {
  return <div className="App">
    <Carousel list={list} />
  </div>;
}

export default App;
