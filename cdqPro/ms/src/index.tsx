import React from 'react';
import ReactDOM from 'react-dom';
import Carousel from './components/Carousel';

const images = [
  'http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg',
  'http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg',
  'http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg',
];

ReactDOM.render(
  <Carousel images={images} />,
  document.getElementById('root')
);

