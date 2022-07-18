
import React from "react";
import "./App.css";
import phoneUrl from './assets/iphone.png';
import tabletUrl from './assets/tablet.png';
import airpodsUrl from './assets/airpods.png';
import Carousel, { Card } from "./components/Carousel";









function App() {

  const lists: Card[] = [
    {
      title: ['xPhone'],
      desc: [
        'Lots to love.Less to spend.',
        'Starting at $399.'
      ],
      style: {
        background: `url(${phoneUrl}) no-repeat center/cover`,
        color: '#fff'
      },
    },
    {
      title: ['Tablet'],
      desc: [
        'Just the right amount of everything',
      ],
      style: {
        background: `url(${tabletUrl}) no-repeat center/cover`,
        color: '#000'
      },
    },
    {
      title: [
        'BUY a Tablet or xPhone for college.',
        'Get airPods'
      ],
      style: {
        background: `url(${airpodsUrl}) no-repeat center/cover`,
        color: '#000'
      },
    },
    {
      title: ['测试多一个list',],
      desc: ['时间 5s'],
      style:{
        background:'#afb8c1'
      }
    }

  ]
  return <div className="App">

    <Carousel time={5000} lists={lists} />
  </div>;
}

export default App;
