/** @format */

import React from 'react';
import Carousel from './components/Carousel';
import './App.css';

interface CardInfo {
  title: string;
  content: string;
  name: string;
}

const cardList: Array<CardInfo> = [
  {
    title: 'xPhone',
    content: 'Lots to love. Less to spend. <br/> Starting at $399.',
    name: 'img-iphone',
  },
  {
    title: 'Tablet',
    content: 'Just the right amount of everything.',
    name: 'img-tablet',
  },
  {
    title: 'Buy a Tablet or xPhone for college. <br/> Get airPods.',
    content: '',
    name: 'img-airpods',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel>
        {cardList.map((info, index) => (
          <div className={info.name} key={index}>
            <div className="title" dangerouslySetInnerHTML={{__html: info.title}}></div>
            <div className="text" dangerouslySetInnerHTML={{__html: info.content}}></div>
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
