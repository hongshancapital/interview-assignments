import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Carousel from './App';

const sliders: Array<{id: number; url: string; title?: string; summary?: string; backgroundColor?: string, style?:{[key: string]: string}}> = [
  {
    id: 3, 
    url: require('./assets/iphone.png').default, 
    title: 'XPhone', 
    summary: 'Lots to love. Less to spend',
    style:{backgroundColor: '#111111'}
  },
  {
    id: 2, 
    url: require('./assets/tablet.png').default, 
    title: 'Tablet', 
    summary: 'Just the right amount of everything',
    style:{backgroundColor: '#fafafa'}
  },
  {
    id: 1, 
    url: require('./assets/airpods.png').default, 
    title: 'Buy a Tablet or XPhone for college', 
    summary: '',
    style:{backgroundColor: '#f1f1f3'}
  },
];

ReactDOM.render(
  <React.StrictMode>
    <Carousel sliders={sliders} />
  </React.StrictMode>,
  document.getElementById('root')
);

