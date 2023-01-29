import { useState } from 'react';
import './App.css';
import { Carousel } from './components/Carousel/index1';

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
const dataSource = [
  { imgSrc: airpods, bgColor: '#F1F1F3', color: '#000', title: 'airpods xxxx' },
  { imgSrc: iphone, bgColor: '#111111', color: '#fff', title: 'ipone XXXX' },
  { imgSrc: tablet, bgColor: '#FAFAFA', color: '#000', title: 'tablet XXXXXX' },
];
const renderCarouselItem = (dataItem: {
  imgSrc: string;
  bgColor: string;
  title: string;
  color: string;
}) => {
  return (
    <div
      className="carouse-item-container"
      style={{ backgroundColor: `${dataItem.bgColor}` }}
    >
      <div className="title" style={{ color: dataItem.color }}>
        {dataItem.title}
      </div>
      <img src={dataItem.imgSrc} draggable={false} alt="" style={{ width: '100%' }} />
    </div>
  );
};
function App() {
  const [index, setIndex] = useState(0);
  return (
    <div className="App">
      <Carousel
        width="100vw"
        height="100vh"
        dataSource={dataSource}
        renderItem={renderCarouselItem}
        activeIndex={index}
        onChange={(i) => {
          // console.log('iiiii:', i);
          setIndex(i);
        }}
      />
    </div>
  );
}

export default App;
